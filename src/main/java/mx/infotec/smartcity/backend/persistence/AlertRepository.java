package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author adrian.molina
 */
public interface AlertRepository extends MongoRepository<Alert, Integer> {
    public List<Alert> findAllByOrderByDataTimeDesc();
    Alert findByAlertType(String alertType);
}