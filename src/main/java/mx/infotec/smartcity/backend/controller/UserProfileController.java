package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.smartcity.backend.model.Address;
import mx.infotec.smartcity.backend.model.Email;
import mx.infotec.smartcity.backend.model.Group;
import mx.infotec.smartcity.backend.model.HealthProfile;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Notification;
import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.model.Vehicle;
import mx.infotec.smartcity.backend.persistence.GroupRepository;
import mx.infotec.smartcity.backend.persistence.NotificationRepository;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import mx.infotec.smartcity.backend.pojo.SubscribedGroup;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.mail.MailService;
import mx.infotec.smartcity.backend.service.recovery.TokenRecoveryService;
import mx.infotec.smartcity.backend.utils.Constants;
import mx.infotec.smartcity.backend.utils.TemplatesEnum;

/**
 * RestService for user-profile.
 *
 * @author Erik Valdivieso
 */
@RestController
@RequestMapping("/user-profile")
public class UserProfileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminUtilsService adminUtilsService;
    @Autowired
    private MailService mailService;
    @Autowired
    private TokenRecoveryService tokenRecoveryService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @Value("${idm.admin.username}")
    private String idmUser;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getByEmail(@RequestParam("email") String email) {
        UserProfile userProfile = userProfileRepository.findByEmail(email);

        if (userProfile != null) {
            return ResponseEntity.accepted().body(userProfile);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public UserProfile getById(@PathVariable String id) {
        return userProfileRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable String id, HttpServletRequest request) {
        IdentityUser identityUser = (IdentityUser) request.getAttribute(Constants.USER_REQUES_KEY);

        if (identityUser == null || identityUser.getUsername().equals(idmUser)
                || (identityUser.getRoles() != null && identityUser.getRoles().contains(Role.SA))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Can't delete this account");
        } else {
            try {
                UserProfile userProfile = userProfileRepository.findOne(id);
                userProfileRepository.delete(id);

                String adminToken = adminUtilsService.getAdmintoken();
                userService.deleteUser(identityUser.getIdmId(), adminToken);

                tokenRecoveryService.deleteAllByEmail(identityUser.getUsername());

                try {
                    Email email = new Email();
                    email.setTo(identityUser.getUsername());

                    Map<String, Object> map = new HashMap<>();

                    if (userProfile == null || userProfile.getName() == null) {
                        map.put(Constants.GENERAL_PARAM_NAME, "User");
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(userProfile.getName());

                        if (userProfile.getFamilyName() == null) {
                            sb.append(' ').append(userProfile.getFamilyName());
                        }

                        map.put(Constants.GENERAL_PARAM_NAME, sb.toString());
                    }

                    email.setContent(map);

                    mailService.sendMail(TemplatesEnum.DELETE_SIMPLE_ACCOUNT, email);
                } catch (Exception ex) {
                    LOGGER.error("Error at send mail", ex);
                }

                return ResponseEntity.accepted().body("deleted");
            } catch (Exception ex) {
                LOGGER.error("Error at delete", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody UserProfile userProfile) {
        if (userProfile.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID must be null");
        } else {
            try {
                return ResponseEntity.accepted().body(userProfileRepository.insert(userProfile));
            } catch (Exception ex) {
                LOGGER.error("Error at insert", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> update(@RequestBody UserProfile userProfile, @PathVariable("id") String id) {
        if (isValid(userProfile)) {
            try {
                UserProfile original = userProfileRepository.findOne(id);
                if (original != null) {
                    if (userProfile.getId() != null) {
                        LOGGER.warn("ID from object is ignored");
                    }

                    userProfile.setId(id);
                    userProfile.setAddresses(original.getAddresses());
                    userProfile.setVehicles(original.getVehicles());

                    userProfileRepository.save(userProfile);

                    return ResponseEntity.accepted().body("updated");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID don't exists");
                }
            } catch (Exception ex) {
                LOGGER.error("Error at update", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        } else {
            LOGGER.error("Invalid userProfile: {}", userProfile);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required fields not present in request");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/health-profile")
    public ResponseEntity<?> getHeathProfile(@PathVariable("id") String id) {

        UserProfile userProfile = null;

        try {
            userProfile = userProfileRepository.findOne(id);
        } catch (Exception ex) {
            LOGGER.error("Error al retrieve userProfile", ex);
        }

        if (userProfile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        } else {
            return ResponseEntity.accepted().body(userProfile.getHealthProfile());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/health-profile")
    public ResponseEntity<?> addHeathProfile(@RequestBody HealthProfile healthProfile, @PathVariable("id") String id) {
        // TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile != null) {

            userProfile.setHealthProfile(healthProfile);

            userProfileRepository.save(userProfile);

            return ResponseEntity.accepted().body(userProfile.getHealthProfile());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/health-profile")
    public ResponseEntity<?> updateHeathProfile(@RequestBody HealthProfile healthProfile,
            @PathVariable("id") String id) {
        // TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile != null) {
            userProfile.setHealthProfile(healthProfile);
            userProfileRepository.save(userProfile);
            return ResponseEntity.accepted().body(userProfile.getHealthProfile());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}/health-profile")
    public ResponseEntity<?> deleteHeathProfile(@PathVariable("id") String id) {
        // TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile != null) {
            userProfile.setHealthProfile(null);
            userProfileRepository.save(userProfile);
            return ResponseEntity.accepted().body(userProfile.getHealthProfile());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/address")
    public ResponseEntity<?> getAddress(@PathVariable("id") String id) {
        UserProfile userProfile = null;

        try {
            userProfile = userProfileRepository.findOne(id);
        } catch (Exception ex) {
            LOGGER.error("Error al retrieve userProfile", ex);
        }

        if (userProfile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        } else if (userProfile.getAddresses() == null) {
            return ResponseEntity.accepted().body(new ArrayList<>(0));
        } else {
            return ResponseEntity.accepted().body(userProfile.getAddresses());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/address/{index}")
    public ResponseEntity<?> getAddress(@PathVariable("id") String id, @PathVariable("index") int index) {
        UserProfile userProfile = null;

        try {
            userProfile = userProfileRepository.findOne(id);
        } catch (Exception ex) {
            LOGGER.error("Error al retrieve userProfile", ex);
        }

        if (userProfile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        } else if (userProfile.getAddresses() == null || userProfile.getAddresses().size() < index) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User without address");
        } else {
            return ResponseEntity.accepted().body(userProfile.getAddresses().get(index));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/address")
    public ResponseEntity<?> addAddress(@RequestBody Address address, @PathVariable("id") String id) {
        if (isValid(address)) {
            UserProfile userProfile = userProfileRepository.findOne(id);

            if (userProfile != null) {
                if (userProfile.getAddresses() == null) {
                    userProfile.setAddresses(new ArrayList<>());
                } else if (address.isFavorite()) {
                    userProfile.getAddresses().forEach((add) -> {
                        add.setFavorite(false);
                    });
                }

                userProfile.getAddresses().add(address);

                userProfileRepository.save(userProfile);

                return ResponseEntity.accepted().body(userProfile.getAddresses());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
            }
        } else {
            LOGGER.error("Invalid address: {}", address);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required fields not present in request");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/address/{index}")
    public ResponseEntity<?> updateAddress(@RequestBody Address address, @PathVariable("id") String id,
            @PathVariable("index") int index) {
        if (isValid(address)) {
            UserProfile userProfile = userProfileRepository.findOne(id);

            if (userProfile != null && userProfile.getAddresses() != null
                    && userProfile.getAddresses().size() > index) {
                
                if (address.isFavorite()) {
                    userProfile.getAddresses().forEach((add) -> {
                        add.setFavorite(false);
                    });
                }
                
                userProfile.getAddresses().set(index, address);
                userProfileRepository.save(userProfile);
                return ResponseEntity.accepted().body(userProfile.getAddresses());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
            }
        } else {
            LOGGER.error("Invalid address: {}", address);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required fields not present in request");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}/address/{index}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") String id, @PathVariable("index") int index) {
        // TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile != null && userProfile.getAddresses() != null && userProfile.getAddresses().size() > index) {
            userProfile.getAddresses().remove(index);
            userProfileRepository.save(userProfile);
            return ResponseEntity.accepted().body(userProfile.getAddresses());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/vehicle")
    public ResponseEntity<?> getVehicle(@PathVariable("id") String id) {
        UserProfile userProfile = null;

        try {
            userProfile = userProfileRepository.findOne(id);
        } catch (Exception ex) {
            LOGGER.error("Error al retrieve userProfile", ex);
        }

        if (userProfile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        } else if (userProfile.getVehicles() == null) {
            return ResponseEntity.accepted().body(new ArrayList<>(0));
        } else {
            return ResponseEntity.accepted().body(userProfile.getVehicles());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/vehicle/{index}")
    public ResponseEntity<?> getVehicle(@PathVariable("id") String id, @PathVariable("index") int index) {
        UserProfile userProfile = null;

        try {
            userProfile = userProfileRepository.findOne(id);
        } catch (Exception ex) {
            LOGGER.error("Error al retrieve userProfile", ex);
        }

        if (userProfile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        } else if (userProfile.getVehicles() == null || userProfile.getVehicles().size() < index) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User without vehicle");
        } else {
            return ResponseEntity.accepted().body(userProfile.getVehicles().get(index));
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/vehicle")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle, @PathVariable("id") String id) {
        if (isValid(vehicle)) {
            UserProfile userProfile = userProfileRepository.findOne(id);

            if (userProfile != null) {
                if (userProfile.getVehicles() == null) {
                    userProfile.setVehicles(new ArrayList<>());
                }

                vehicle.setDatecreated(new Date());
                vehicle.setDateModified(new Date());

                userProfile.getVehicles().add(vehicle);

                userProfileRepository.save(userProfile);

                return ResponseEntity.accepted().body(userProfile.getVehicles());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
            }
        } else {
            LOGGER.error("Invalid vehicle: {}", vehicle);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required fields not present in request");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/groups")
    public ResponseEntity<?> getGroups(@PathVariable("id") String id) {

        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile != null) {
            return sendSubscribedGroups(userProfile);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/notifications")
    public ResponseEntity<?> getUserGroups(@PathVariable("id") String id) {

        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile != null) {
            List<Notification> notifications = this.notificationRepository.findAll();
            List<Notification> userNotifications = new ArrayList<Notification>();

            for (Group group : userProfile.getGroups()) {
                for (String userNotification : group.getNotificationIds()) {
                    for (Notification notification : notifications) {
                        if (userNotification.equals(notification.getId())) {
                            if (!userNotifications.contains(notification)) {
                                userNotifications.add(notification);
                            }
                        }
                    }
                }
            }
            return ResponseEntity.accepted().body(userNotifications);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    private ResponseEntity<?> sendSubscribedGroups(UserProfile userProfile) {
        List<Group> adminGroups = this.groupRepository.findAll();
        return ResponseEntity.accepted().body(createGroupPojo(adminGroups, userProfile.getGroups()));
    }

    private List<SubscribedGroup> createGroupPojo(List<Group> adminGroups, List<Group> userGroups) {
        if (adminGroups != null && !adminGroups.isEmpty()) {

            List<SubscribedGroup> subscribedGroups = new ArrayList<SubscribedGroup>();
            for (Group adminGroup : adminGroups) {
                SubscribedGroup subscribedGroup = new SubscribedGroup();
                subscribedGroup.convert(adminGroup);
                if (userGroups != null) {
                    for (Group userGroup : userGroups) {
                        if (userGroup != null && userGroup.getId() != null
                                && userGroup.getId().equals(adminGroup.getId())) {
                            subscribedGroup.setSubscribed(true);
                        }
                    }
                }
                subscribedGroups.add(subscribedGroup);
            }

            return subscribedGroups;
        }
        return new ArrayList<SubscribedGroup>();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}/groups")
    public ResponseEntity<?> changeGroups(@RequestBody List<SubscribedGroup> subscribedGroups,
            @PathVariable("id") String id) {

        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile != null) {

            List<Group> gg = userProfile.getGroups();
            if (userProfile.getGroups() == null) {
                userProfile.setGroups(new ArrayList<>());
            }
            List<Group> groups = convertSubscribedGroupsToGroups(subscribedGroups, userProfile.getGroups());
            userProfile.setGroups(groups);

            userProfileRepository.save(userProfile);

            return sendSubscribedGroups(userProfile);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    private List<Group> convertSubscribedGroupsToGroups(List<SubscribedGroup> subscribedGroups, List<Group> groups) {
        // if the array sended to the services is empty no changes will be made
        if (subscribedGroups == null || subscribedGroups.isEmpty()) {
            return groups;
        }
        removeOrAddUserGroup(subscribedGroups, groups);
        return groups;
    }

    private void removeOrAddUserGroup(List<SubscribedGroup> subscribedGroups, List<Group> groups) {
        for (SubscribedGroup subscribed : subscribedGroups) {
            boolean exist = false;
            if (subscribed.isSubscribed()) {
                // check if exist
                for (Group group : groups) {
                    if (subscribed.getId() != null && group.getId().equals(subscribed.getId())) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {

                    groups.add(convertSubscribedToGroup(subscribed));
                }
            } else {
                // check if needs to be removed
                for (Group group : groups) {
                    if (subscribed.getId() != null && group.getId().equals(subscribed.getId())) {
                        groups.remove(group);
                        break;
                    }
                }
            }
        }
    }

    private Group convertSubscribedToGroup(SubscribedGroup subscribed) {
        Group group = new Group();
        group.setId(subscribed.getId());
        group.setGroup(subscribed.getGroup());
        group.setDateCreated(subscribed.getDateCreated());
        group.setDateModified(subscribed.getDateModified());
        group.setNotificationIds(subscribed.getNotificationIds());
        group.setType(subscribed.getType());
        return group;
    }

0       @RequestMapping(method = RequestMethod.POST, value = "/{id}/groups")
    public ResponseEntity<?> addGroups(@RequestBody List<Group> groups, @PathVariable("id") String id) {

        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile != null) {

            List<Group> gg = userProfile.getGroups();
            if (userProfile.getGroups() == null) {
                userProfile.setGroups(new ArrayList<>());
            }

            userProfile.setGroups(groups);

            userProfileRepository.save(userProfile);

            return ResponseEntity.accepted().body(userProfile.getGroups());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/vehicle/{index}")
    public ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle, @PathVariable("id") String id,
            @PathVariable("index") int index) {
        if (isValid(vehicle)) {
            UserProfile userProfile = userProfileRepository.findOne(id);

            if (userProfile != null && userProfile.getVehicles() != null && userProfile.getVehicles().size() > index) {
                vehicle.setDateModified(new Date());
                userProfile.getVehicles().set(index, vehicle);
                userProfileRepository.save(userProfile);
                return ResponseEntity.accepted().body(userProfile.getVehicles());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
            }
        } else {
            LOGGER.error("Invalid vehicle: {}", vehicle);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required fields not present in request");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}/vehicle/{index}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") String id, @PathVariable("index") int index) {
        // TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile != null && userProfile.getVehicles() != null && userProfile.getVehicles().size() > index) {
            userProfile.getVehicles().remove(index);
            userProfileRepository.save(userProfile);
            return ResponseEntity.accepted().body(userProfile.getVehicles());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public ResponseEntity<?> updateEmail(@RequestBody UserProfile userProfile, @PathVariable("id") String id,
            HttpServletRequest request) {
        if (userProfile.getEmail() != null) {
            try {
                IdentityUser logedUser = (IdentityUser) request.getAttribute(Constants.USER_REQUES_KEY);
                String tokenAdmin = adminUtilsService.getAdmintoken();
                if (logedUser != null) {
                    if (userProfile.getId() != null) {
                        LOGGER.warn("ID from object is ignored");
                    }

                    userProfile.setId(logedUser.getMongoId());
                    userProfileRepository.save(userProfile);
                    CreateUser user = new CreateUser();
                    user.getUser().setId(logedUser.getIdmId());
                    user.getUser().setName(userProfile.getEmail());
                    userService.updateUser(logedUser.getIdmId(), tokenAdmin, user);

                    return ResponseEntity.accepted().body("updated");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID don't exists");
                }
            } catch (Exception ex) {
                LOGGER.error("Error at update", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        } else {
            LOGGER.error("Invalid userProfile: {}", userProfile);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Required field email not present in request");
        }
    }

    private boolean isValid(UserProfile userProfile) {
        Date currentDate = new Date();

        return userProfile != null && userProfile.getName() != null && userProfile.getFamilyName() != null
                && (userProfile.getBirthDate() == null
                        || (userProfile.getBirthDate() != null && userProfile.getBirthDate().before(currentDate)));
    }

    private boolean isValid(Address address) {
        return address != null && address.getStreet() != null && address.getLocality() != null;
    }

    private boolean isValid(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        } else if (vehicle.getName() == null || vehicle.getVehicleType() == null || vehicle.getFuelType() == null) {
            return false;
        } else if (vehicle.getVehicleType().getIncludeBrandModel()) {
            return vehicle.getBrandName() != null && vehicle.getModelName() != null;
        } else {
            return true;
        }
    }
}
