package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.Locality;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Erik Valdivieso
 */
public interface LocalityRepository extends MongoRepository<Locality, Integer> {
    
    @Query(value="{ 'region.$id' : ?0 }")
    List<Locality> findByRegionId(int regionId);
    
}
