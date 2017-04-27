/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.Notification;
import mx.infotec.smartcity.backend.model.SubNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Manu
 */
public interface SubNotificationRepository extends MongoRepository<SubNotification, Integer> {
    
    @Query(value="{ 'notification.$id' : ?0 }")
    List<SubNotification> findByNotificationId(int notificationId);
}
