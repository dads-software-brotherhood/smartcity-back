package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.transport.TransportSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Erik Valdivieso
 */
public interface TransportScheduleRepository extends MongoRepository<TransportSchedule, String> {

    @Query(value = "{'routeName': {$regex : ?0, $options: 'i'}}")
    List<TransportSchedule> findByRouteName(String routeName);
    
}
