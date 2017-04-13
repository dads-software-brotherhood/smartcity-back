package mx.infotec.smartcity.backend.persistence;

import mx.infotec.smartcity.backend.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author adrian.molina
 */
public interface NotificationRepository extends MongoRepository<Notification, Integer> {
    
    
}