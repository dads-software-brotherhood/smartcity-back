package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.Locality;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Erik Valdivieso
 */
public interface LocalityRepository extends MongoRepository<Locality, Integer> {
    
    List<Locality> findByRegionId(int regionId);
    
}
