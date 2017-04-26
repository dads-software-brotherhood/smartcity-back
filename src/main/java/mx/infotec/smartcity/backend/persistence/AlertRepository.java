package mx.infotec.smartcity.backend.persistence;

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
    public List<Alert> findAllByOrderByDataTimeDesc();
    public List<Alert> findByAlertTypeInOrderByDataTimeDesc(List<String> alertTypes, Pageable pageable);
    public List<Alert> findByAlertTypeOrderByDataTimeDesc(String alertType, Pageable pageable);
    public List<Alert> findByAlertTypeAndSubtypeAlertOrderByDataTimeDesc(String alertType, String subalertType, Pageable pageable);
    
    public List<Alert> findByRefUserOrderByDataTimeDesc(String id, Pageable pageable);
    public List<Alert> findByRefUserAndAlertTypeOrderByDataTimeDesc(String id, String alertType, Pageable pageable);
    public List<Alert> findByRefUserAndAlertTypeAndSubtypeAlertOrderByDataTimeDesc(String id, String alertType, String subalertType, Pageable pageable);
}