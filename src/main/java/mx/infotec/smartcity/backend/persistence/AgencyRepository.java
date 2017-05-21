package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.transport.Agency;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Infotec
 */
public interface AgencyRepository extends MongoRepository<Agency, String> {
    
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Agency> findByName(String name);
}
 