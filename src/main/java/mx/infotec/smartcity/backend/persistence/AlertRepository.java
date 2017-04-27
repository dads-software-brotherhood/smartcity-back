package mx.infotec.smartcity.backend.persistence;

import java.util.Date;
import java.util.List;
import mx.infotec.smartcity.backend.model.Alert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author adrian.molina
 */
public interface AlertRepository extends MongoRepository<Alert, Integer> {
    public List<Alert> findAllByOrderByDateTimeDesc();
    public List<Alert> findAllByOrderByDateTimeDesc(Pageable pageable);
    
    public List<Alert> findByDateTimeBetweenOrderByDateTimeDesc(Date start, Date end, Pageable pageable);
    
    public List<Alert> findByAlertTypeInOrderByDateTimeDesc(List<String> alertTypes, Pageable pageable);
    public List<Alert> findByAlertTypeOrderByDateTimeDesc(String alertType, Pageable pageable);
    public List<Alert> findByAlertTypeAndEventObservedOrderByDateTimeDesc(String alertType, String subalertType, Pageable pageable);
    
    public List<Alert> findByAlertTypeAndDateTimeBetweenOrderByDateTimeDesc(String alertType, Date start, Date end, Pageable pageable);
    public List<Alert> findByAlertTypeAndEventObservedAndDateTimeBetweenOrderByDateTimeDesc(String alertType, String subalertType, Date start, Date end, Pageable pageable);
    
    public List<Alert> findByRefUserOrderByDateTimeDesc(String id, Pageable pageable);
    public List<Alert> findByRefUserAndAlertTypeOrderByDateTimeDesc(String id, String alertType, Pageable pageable);
    public List<Alert> findByRefUserAndAlertTypeAndEventObservedOrderByDateTimeDesc(String id, String alertType, String subalertType, Pageable pageable);
    
    public List<Alert> findByRefUserAndDateTimeBetweenOrderByDateTimeDesc(String id, Date start, Date end, Pageable pageable);
    public List<Alert> findByRefUserAndAlertTypeAndDateTimeBetweenOrderByDateTimeDesc(String id, String alertType, Date start, Date end, Pageable pageable);
    public List<Alert> findByRefUserAndAlertTypeAndEventObservedAndDateTimeBetweenOrderByDateTimeDesc(String id, String alertType, String subalertType, Date start, Date end, Pageable pageable);
}