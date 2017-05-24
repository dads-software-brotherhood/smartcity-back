package mx.infotec.smartcity.backend.persistence;

import mx.infotec.smartcity.backend.model.transport.TransportSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Erik Valdivieso
 */
public interface TransportScheduleRepository extends MongoRepository<TransportSchedule, String> {
    
//    @Query(value = "{'name': {$regex : ?0, $options: 'i'}, 'frequencyHours': ?1, 'frequencyMins' : '?2', 'agency.id': ?3}")
//    Page<TransportSchedule> findByNameFrequencyAgency(String name, Integer frequencyHours, Integer frequencyMins, String idAgency);
//    
}
