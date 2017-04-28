package mx.infotec.smartcity.backend.listener;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.model.RoleId;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.User_;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.RoleAssignments;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Roles;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.SelfRole;
import mx.infotec.smartcity.backend.utils.RoleUtil;

@Component
public class CreateRolesAplicationListener implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

    @Value("${sa.account}")
    private String saName;

    @Value("${sa.passwd}")
    private String saPasswd;

    @Autowired
    @Qualifier("adminUtils")
    private AdminUtilsService adminUtils;

    @Autowired
    @Qualifier("keystoneLoginService")
    private LoginService loginService;

    @Autowired
    @Qualifier("keystoneRoleService")
    private RoleService roleService;

    @Autowired
    @Qualifier("keystoneUserService")
    private UserService userService;

    public void createRoles() {
        String adminToken = "";
        LOGGER.info("Starting the aplication event");

        try {
            adminToken = adminUtils.getAdmintoken();
            Roles roles = roleService.getAllRolesLikeRoles(adminToken);
            HashMap<String, RoleId> rolesId = new HashMap<>();
            for (Role role : Role.values()) {
                Boolean create = true;
                for (mx.infotec.smartcity.backend.service.keystone.pojo.roles.Role roleKey : roles.getRoles()) {
                    if (roleKey.getName().equalsIgnoreCase(role.name())) {
                        create = false;
                        rolesId.put(role.name(), new RoleId(role, roleKey.getId()));
                    }

                }
                if (create) {
                    LOGGER.debug("It will be create the role {}", role.name());
                    SelfRole newRole = new SelfRole(role.name().toLowerCase());
                    newRole = roleService.createRole(newRole, adminToken);
                    rolesId.put(role.name(), new RoleId(role, newRole.getRole().getId()));
                }
            }
            RoleUtil.init(rolesId);

        } catch (ServiceException e) {
            LOGGER.error("Error at roles creation", e);
        } finally {
            if (adminToken != null && !adminToken.isEmpty()) {
                loginService.invalidToken(adminToken);
            }
        }

    }

    private void createDefaultAdmin() {
        String adminToken = "";
        try {
            adminToken = adminUtils.getAdmintoken();

            List<RoleAssignments> roleAssignamentList = roleService.getUsersByRoleId(RoleUtil.getIdRole(Role.SA),
                    adminToken);
            if (roleAssignamentList == null || roleAssignamentList.isEmpty()) {
                LOGGER.debug("It will be create a default SA user, with username: {}", saName);

                User_ user = new User_();
                user.setEnabled(true);
                user.setName(saName);
                user.setPassword(saPasswd);
                CreateUser createUser = new CreateUser(user);
                CreateUser createdUser = userService.createUserWithRole(createUser, Role.SA);

                // Temporal disable

                // if (createdUser != null) {
                // roleService.assignRoleToUserOnDefaultDomain(RoleUtil.getIdRole(Role.USER),
                // createdUser.getUser().getId(), adminToken);
                // } else {
                // throw new ServiceException("Error to create new user");
                // }

            }
        } catch (ServiceException e) {
            LOGGER.debug("Error to create default user with SA role, cause: ", e);
        } finally {
            if (adminToken != null && !adminToken.isEmpty()) {
                loginService.invalidToken(adminToken);
            }
        }

    }

    /*
     * @Override public void run(String... args) throws Exception {
     * createRoles();
     * 
     * }
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent arg0) {
        createRoles();
        createDefaultAdmin();
    }

}
