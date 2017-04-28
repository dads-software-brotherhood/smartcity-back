/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.persistence;

import mx.infotec.smartcity.backend.model.VehicleType;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author jose.gomez
 */
public interface VehicleTypeRepository extends MongoRepository<VehicleType, Integer> {
    VehicleType findByName(String vehicleTypeName);
}
