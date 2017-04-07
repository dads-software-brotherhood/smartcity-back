package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.Region;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Erik Valdivieso
 */
public interface RegionRepository extends MongoRepository<Region, Integer> {
    
    @Query(value="{ 'country.$id' : ?0 }")
    List<Region> findByCountryId(int countryId);
    
}
