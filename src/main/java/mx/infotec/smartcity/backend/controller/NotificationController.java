package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.List;
import mx.infotec.smartcity.backend.model.Notification;
import mx.infotec.smartcity.backend.persistence.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Adrian Molina
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;
    
    @RequestMapping(method = RequestMethod.GET)    
    public List<Notification> getAll() {
        List<Notification> res = notificationRepository.findAll();
        
        if (res == null) {
            return new ArrayList<>();
        } else {
            return res;
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Notification getById(@PathVariable int id) {
        return notificationRepository.findOne(id);
    }
    
}
