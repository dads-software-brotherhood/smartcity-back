/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.List;
import mx.infotec.smartcity.backend.model.SubNotification;
import mx.infotec.smartcity.backend.persistence.SubNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Manu
 */
@RestController
@RequestMapping("/subnotifications")
public class SubNotificationController {
    
    @Autowired
    private SubNotificationRepository subNotificationRepository;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<SubNotification> getAll(@RequestParam(value = "notificationId", required = true) Integer notificationId) {
        List<SubNotification> res;
        
        res = subNotificationRepository.findByNotificationId(notificationId);
        if (res == null) {
            return new ArrayList<>();
        } else {
            return res;
        }
    }
}
