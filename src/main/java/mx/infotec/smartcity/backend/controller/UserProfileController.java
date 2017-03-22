package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.infotec.smartcity.backend.model.Address;
import mx.infotec.smartcity.backend.model.HealthProfile;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.model.Vehicle;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> deleteByID(@PathVariable String id) {
        try {
            userProfileRepository.delete(id);
            return ResponseEntity.accepted().body("deleted");
        } catch (Exception ex) {
            LOGGER.error("Error at delete", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
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
        try {
            if (userProfileRepository.exists(id)) {
                if (userProfile.getId() != null) {
                    LOGGER.warn("ID from object is ignored");
                }

                userProfile.setId(id);
                userProfileRepository.save(userProfile);

                return ResponseEntity.accepted().body("updated");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID don't exists");
            }
        } catch (Exception ex) {
            LOGGER.error("Error at update", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/health-profile")
    public List<HealthProfile> getHeathProfile(@PathVariable("id") String id) {
        //TODO: Agregar validaciones y bloques de try/catch
        
        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile == null) {
            return null;
        } else {
            return userProfile.getHealthProfiles();
        }
    }    
    
    @RequestMapping(method = RequestMethod.POST, value = "/{id}/health-profile")
    public ResponseEntity<?> addHeathProfile(@RequestBody HealthProfile healthProfile, @PathVariable("id") String id) {
        //TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);
        
        if (userProfile != null) {
            if (userProfile.getHealthProfiles() == null) {
                userProfile.setHealthProfiles(new ArrayList<>());
            }
            
            userProfile.getHealthProfiles().add(healthProfile);
            
            userProfileRepository.save(userProfile);
            
            return ResponseEntity.accepted().body(userProfile.getHealthProfiles()); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/health-profile/{index}")
    public ResponseEntity<?> updateHeathProfile(@RequestBody HealthProfile healthProfile, @PathVariable("id") String id, @PathVariable("index") int index) {
        //TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);
        
        if (userProfile != null && userProfile.getHealthProfiles() != null && userProfile.getHealthProfiles().size() > index) {
            userProfile.getHealthProfiles().set(index, healthProfile);
            userProfileRepository.save(userProfile);
            return ResponseEntity.accepted().body(userProfile.getHealthProfiles()); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/health-profile/{index}")
    public ResponseEntity<?> deleteHeathProfile(@PathVariable("id") String id, @PathVariable("index") int index) {
        //TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);
        
        if (userProfile != null && userProfile.getHealthProfiles() != null && userProfile.getHealthProfiles().size() > index) {
            userProfile.getHealthProfiles().remove(index);
            userProfileRepository.save(userProfile);
            return ResponseEntity.accepted().body(userProfile.getHealthProfiles()); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/address")
    public List<Address> getAddress(@PathVariable("id") String id) {
        //TODO: Agregar validaciones y bloques de try/catch
        
        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile == null) {
            return null;
        } else {
            return userProfile.getAddresses();
        }
    }    
    
    @RequestMapping(method = RequestMethod.POST, value = "/{id}/address")
    public ResponseEntity<?> addAddress(@RequestBody Address address, @PathVariable("id") String id) {
        //TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);
        
        if (userProfile != null) {
            if (userProfile.getAddresses()== null) {
                userProfile.setAddresses(new ArrayList<>());
            }
            
            userProfile.getAddresses().add(address);
            
            userProfileRepository.save(userProfile);
            
            return ResponseEntity.accepted().body(userProfile.getAddresses()); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/address/{index}")
    public ResponseEntity<?> updateAddress(@RequestBody Address address, @PathVariable("id") String id, @PathVariable("index") int index) {
        //TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);
        
        if (userProfile != null && userProfile.getAddresses()!= null && userProfile.getAddresses().size() > index) {
            userProfile.getAddresses().set(index, address);
            userProfileRepository.save(userProfile);
            return ResponseEntity.accepted().body(userProfile.getAddresses()); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/address/{index}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") String id, @PathVariable("index") int index) {
        //TODO: Agregar validaciones y bloques de try/catch
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
    public List<Vehicle> getVehicle(@PathVariable("id") String id) {
        //TODO: Agregar validaciones y bloques de try/catch
        
        UserProfile userProfile = userProfileRepository.findOne(id);

        if (userProfile == null) {
            return null;
        } else {
            return userProfile.getVehicles();
        }
    }    
    
    @RequestMapping(method = RequestMethod.POST, value = "/{id}/vehicle")
    public ResponseEntity<?> addVehicle(@RequestBody Vehicle vehicle, @PathVariable("id") String id) {
        //TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);
        
        if (userProfile != null) {
            if (userProfile.getVehicles()== null) {
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
    }
    
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}/vehicle/{index}")
    public ResponseEntity<?> updateVehicle(@RequestBody Vehicle vehicle, @PathVariable("id") String id, @PathVariable("index") int index) {
        //TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);
        
        if (userProfile != null && userProfile.getVehicles() != null && userProfile.getVehicles().size() > index) {
            vehicle.setDateModified(new Date());
            userProfile.getVehicles().set(index, vehicle);
            userProfileRepository.save(userProfile);
            return ResponseEntity.accepted().body(userProfile.getVehicles()); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{id}/vehicle/{index}")
    public ResponseEntity<?> deleteVehicle(@PathVariable("id") String id, @PathVariable("index") int index) {
        //TODO: Agregar validaciones y bloques de try/catch
        UserProfile userProfile = userProfileRepository.findOne(id);
        
        if (userProfile != null && userProfile.getVehicles() != null && userProfile.getVehicles().size() > index) {
            userProfile.getVehicles().remove(index);
            userProfileRepository.save(userProfile);
            return ResponseEntity.accepted().body(userProfile.getVehicles()); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserProfile not valid");
        }
    }
}
