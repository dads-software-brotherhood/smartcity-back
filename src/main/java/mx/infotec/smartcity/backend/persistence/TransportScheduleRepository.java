package mx.infotec.smartcity.backend.persistence;

import mx.infotec.smartcity.backend.model.transport.TransportSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Erik Valdivieso
 */
public interface TransportScheduleRepository extends MongoRepository<TransportSchedule, String> {
}
