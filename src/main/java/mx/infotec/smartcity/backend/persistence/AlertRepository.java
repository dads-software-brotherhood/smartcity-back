package mx.infotec.smartcity.backend.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import mx.infotec.smartcity.backend.model.Alert;
import mx.infotec.smartcity.backend.model.Notification;

/**
 *
 * @author adrian.molina
 */
public interface AlertRepository extends MongoRepository<Alert, Integer> {
    public List<Alert> findAllByOrderByDateTimeDesc();

    public List<Alert> findByAlertType(String id);
    
    public Page<Alert> findAllByOrderByDateTimeDesc(Pageable pageable);

    public Page<Alert> findByDateTimeBetweenOrderByDateTimeDesc(Date start, Date end, Pageable pageable);

    public Page<Alert> findByAlertTypeInOrderByDateTimeDesc(List<String> alertTypes, Pageable pageable);

    public Page<Alert> findByAlertTypeInAndDateTimeBetweenOrderByDateTimeDesc(List<String> alertTypes, Date start,
            Date end, Pageable pageable);

    public Page<Alert> findByAlertTypeOrderByDateTimeDesc(String alertType, Pageable pageable);

    public Page<Alert> findByAlertTypeAndEventObservedOrderByDateTimeDesc(String alertType, String subalertType,
            Pageable pageable);

    public Page<Alert> findByAlertTypeAndDateTimeBetweenOrderByDateTimeDesc(String alertType, Date start, Date end,
            Pageable pageable);

    public Page<Alert> findByAlertTypeAndEventObservedAndDateTimeBetweenOrderByDateTimeDesc(String alertType,
            String subalertType, Date start, Date end, Pageable pageable);

    public Page<Alert> findByRefUserOrderByDateTimeDesc(String id, Pageable pageable);

    public Page<Alert> findByRefUserAndAlertTypeOrderByDateTimeDesc(String id, String alertType, Pageable pageable);

    public Page<Alert> findByRefUserAndAlertTypeAndEventObservedOrderByDateTimeDesc(String id, String alertType,
            String subalertType, Pageable pageable);

    public Page<Alert> findByRefUserAndDateTimeBetweenOrderByDateTimeDesc(String id, Date start, Date end,
            Pageable pageable);

    public Page<Alert> findByRefUserAndAlertTypeAndDateTimeBetweenOrderByDateTimeDesc(String id, String alertType,
            Date start, Date end, Pageable pageable);

    public Page<Alert> findByRefUserAndAlertTypeAndEventObservedAndDateTimeBetweenOrderByDateTimeDesc(String id,
            String alertType, String subalertType, Date start, Date end, Pageable pageable);

	
}