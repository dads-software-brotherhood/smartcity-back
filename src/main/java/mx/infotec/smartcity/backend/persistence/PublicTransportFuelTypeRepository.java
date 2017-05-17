package mx.infotec.smartcity.backend.persistence;

import mx.infotec.smartcity.backend.model.transport.PublicTransportFuelType;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Infotec
 */
public interface PublicTransportFuelTypeRepository extends MongoRepository<PublicTransportFuelType, Integer> {
    
}
