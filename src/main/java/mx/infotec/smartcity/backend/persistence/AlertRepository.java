package mx.infotec.smartcity.backend.persistence;

import java.util.Date;
import java.util.List;
import mx.infotec.smartcity.backend.model.Alert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author adrian.molina
 */
public interface AlertRepository extends MongoRepository<Alert, Integer> {
    public List<Alert> findAllByOrderByDateTimeDesc();
    public List<Alert> findByAlertTypeInOrderByDateTimeDesc(List<String> alertTypes, Pageable pageable);
    public List<Alert> findByAlertTypeOrderByDateTimeDesc(String alertType, Pageable pageable);
    public List<Alert> findByAlertTypeAndSubtypeAlertOrderByDateTimeDesc(String alertType, String subalertType, Pageable pageable);
    
    public List<Alert> findByRefUserOrderByDateTimeDesc(String id, Pageable pageable);
    public List<Alert> findByRefUserAndAlertTypeOrderByDateTimeDesc(String id, String alertType, Pageable pageable);
    public List<Alert> findByRefUserAndAlertTypeAndSubtypeAlertOrderByDateTimeDesc(String id, String alertType, String subalertType, Pageable pageable);
    
//    public List<Alert> findByRefUserAndDateTimeOrderByDateTimeDesc(String id, @Temporal(TemporalType.DATE) Date date, Pageable pageable);
//    public List<Alert> findByRefUserAndAlertTypeAndDateTimeOrderByDateTimeDesc(String id, String alertType, Pageable pageable);
//    public List<Alert> findByRefUserAndAlertTypeAndSubtypeAlertAndDateTimeOrderByDateTimeDesc(String id, String alertType, String subalertType, Pageable pageable);
}