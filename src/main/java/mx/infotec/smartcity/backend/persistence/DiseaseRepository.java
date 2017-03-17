package mx.infotec.smartcity.backend.persistence;

import mx.infotec.smartcity.backend.model.Disease;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Erik Valdivieso
 */
public interface DiseaseRepository extends MongoRepository<Disease, String> {
    
    Disease findByName(String name);
    
}
