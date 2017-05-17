package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.transport.Agency;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Infotec
 */
public interface AgencyRepository extends MongoRepository<Agency, Integer> {
    
    List<Agency> findByName(String name);
}
 