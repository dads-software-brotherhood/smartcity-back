package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.Region;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Erik Valdivieso
 */
public interface RegionRepository extends MongoRepository<Region, Integer> {
    
    List<Region> findByCountryId(int countryId);
    
}
