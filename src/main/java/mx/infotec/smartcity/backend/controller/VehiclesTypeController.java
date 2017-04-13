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
import mx.infotec.smartcity.backend.model.VehicleType;
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
 /*
 * @author jose.gomez
 */

@RestController
@RequestMapping("/vehicletype")
public class VehiclesTypeController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(VehiclesTypeController.class);
    
    @Autowired
    private VehicleTypeRepository vehicleTypesRepository;
    
    @RequestMapping(method = RequestMethod.GET)    
    public List<VehicleType> getAll() {
        List<VehicleType> res = vehicleTypesRepository.findAll();   
        if (res == null)
            return new ArrayList<>();
        else
            return res;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public VehicleType getById(@PathVariable String id) {
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
  public ResponseEntity<?> update(@RequestBody VehicleType vehiclesType, @PathVariable("id") String id) {
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
  
  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  public ResponseEntity<?> deleteByID(@PathVariable String id) {
    try {
      vehicleTypesRepository.delete(id);
      return ResponseEntity.accepted().body("deleted");
    } catch (Exception ex) {
      LOGGER.error("Error at delete", ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }
}
