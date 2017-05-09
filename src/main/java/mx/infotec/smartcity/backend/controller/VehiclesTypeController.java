/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import mx.infotec.smartcity.backend.model.UserModel;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.model.Vehicle;
import mx.infotec.smartcity.backend.model.VehicleType;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import mx.infotec.smartcity.backend.persistence.VehicleTypeRepository;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import org.springframework.data.domain.Sort;
 /*
 * @author jose.gomez
 */

@RestController
@RequestMapping("/vehicletype")
public class VehiclesTypeController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VehiclesTypeController.class);
    
    @Autowired
    private VehicleTypeRepository vehicleTypesRepository;
    @Autowired
    private UserService keystoneUserService;
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    @RequestMapping(method = RequestMethod.GET)    
    public List<VehicleType> getAll() {
        List<VehicleType> res = vehicleTypesRepository.findAll();   
        if (res == null)
            return new ArrayList<>();
        else
            return res;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public VehicleType getById(@PathVariable Integer id) {
        return vehicleTypesRepository.findOne(id);
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> add(@Valid @RequestBody VehicleType vehicleType) {
    if (vehicleType.getId() != null)
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID must be null");
    else {
      try {
        vehicleType.setDateCreated(new Date());
        vehicleType.setDateModified(new Date());
        //Add this code lines to set numeric id to vehicle type////////////////////////////////////////
        Integer newId = 0;
        long countVehicleType = vehicleTypesRepository.count();
        if(countVehicleType > 0)
        {
        newId = vehicleTypesRepository.findAll(new Sort(Sort.Direction.DESC, "id")).get(0).getId();
        vehicleType.setId(newId + 1);
        }
        else 
            vehicleType.setId(1);
        //////////////////////////////////////////////////////////////////////////////////////////////
        VehicleType VehicleRepro = vehicleTypesRepository.insert(vehicleType);
        return ResponseEntity.accepted().body(VehicleRepro);
      } catch (Exception ex) {
        LOGGER.error("Error at insert", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
      }
    }
  }
    
  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
  value = "/{id}")
  public ResponseEntity<?> update(@RequestBody VehicleType vehiclesType, @PathVariable("id") Integer id) {
    try {
      if (vehicleTypesRepository.exists(id)) {
        if (vehiclesType.getId() != null)
          LOGGER.warn("ID from object is ignored");
        
        vehiclesType.setDateModified(new Date());
        vehiclesType.setId(id);
        vehicleTypesRepository.save(vehiclesType);

        return ResponseEntity.accepted().body("updated");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID don't exists");
      }
    } catch (Exception ex) {
      LOGGER.error("Error at update", ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }
  
  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}/type/{type}")
  public ResponseEntity<?> deleteByID(@PathVariable Integer id,
                                      @PathVariable("type") String type) throws ServiceException {
    try {
      List<UserModel> userModelList = keystoneUserService.getUserModelList();
      UserProfile user;
      boolean existCar = false;
      List<Vehicle> lstVehiculos;
      
      if (userModelList != null && !userModelList.isEmpty()) {
          for(int i=0; i < userModelList.size(); i++)
          {
              user = new UserProfile();
              user = userProfileRepository.findByEmail(userModelList.get(i).getEmail());
              if (user != null)
              {
                lstVehiculos = user.getVehicles();
                if(lstVehiculos != null)
                {
                for(int j=0; j < lstVehiculos.size(); j++)
                {
                    if(lstVehiculos.get(j).getType().equals(type))
                    {
                        existCar = true;
                        break;
                    }
                }
                if(existCar)
                {
                    break;
                }
              }
              }
          }
      }
      if(existCar)
          {
            return ResponseEntity.badRequest().body("Error");
          }
          else
          {
            vehicleTypesRepository.delete(id);
            return ResponseEntity.accepted().body("deleted");
          }
    } catch (Exception ex) {
      LOGGER.error("Error at delete", ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }
}
