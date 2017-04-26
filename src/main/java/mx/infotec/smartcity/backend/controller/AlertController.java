package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import mx.infotec.smartcity.backend.model.Alert;
import mx.infotec.smartcity.backend.model.Group;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.persistence.AlertRepository;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import mx.infotec.smartcity.backend.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Adrian Molina
 */
@RestController
@RequestMapping("/alerts")
public class AlertController {

    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private UserProfileRepository userProfileRepository;
    
    private int                 SIZE   = 5;
    
    @RequestMapping(method = RequestMethod.GET)    
    public List<Alert> getAll() {
        List<Alert> res = alertRepository.findAllByOrderByDateTimeDesc();
        
        if (res == null) {
            return new ArrayList<>();
        } else {
            return res;
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/page/{page}/{size}")
    public List<Alert> getByPageSize(@PathVariable("page") String page,
    @PathVariable("size") String size) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size), new Sort(Sort.Direction.DESC, "id"));
        return alertRepository.findAll(pageable).getContent();
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Alert getById(@PathVariable int id) {
        return alertRepository.findOne(id);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}/page/{page}/items/{size}")    
    public List<Alert> getAllByUser(@PathVariable("id") String id,
                                    @PathVariable("page") String page,
                                    @PathVariable("size") String size, 
                                    HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        UserProfile profile = userProfileRepository.findOne(id);
        List<String> alertTypes = new ArrayList<>();
        profile.getGroups().forEach((group) -> {
            alertTypes.addAll(group.getNotificationIds());
        });
        List<Alert> res = alertRepository.findByAlertTypeInOrderByDateTimeDesc(alertTypes, pageable);
        return res;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/type/{alertType}/page/{page}/items/{size}")    
    public List<Alert> getAllByAlert(@PathVariable("alertType") String alertType, 
                                    @PathVariable("page") String page,
                                    @PathVariable("size") String size, 
                                    HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        List<Alert> res = alertRepository.findByAlertTypeOrderByDateTimeDesc(alertType, pageable);
        return res;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/type/{alertType}/subtype/{subtype}/page/{page}/items/{size}")    
    public List<Alert> getAllByAlertAndSubalert(@PathVariable("alertType") String alertType, 
                                                @PathVariable("subtype") String subtype, 
                                                @PathVariable("page") String page,
                                                @PathVariable("size") String size, 
                                                HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        List<Alert> res = alertRepository.findByAlertTypeAndSubtypeAlertOrderByDateTimeDesc(alertType, subtype, pageable);
        return res;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/page/{page}/items/{size}")    
    public List<Alert> getAllEventsByUser(@PathVariable("id") String id, 
                                    @PathVariable("page") String page,
                                    @PathVariable("size") String size, 
                                    HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        List<Alert> res = alertRepository.findByRefUserOrderByDateTimeDesc(id, pageable);
        return res;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/type/{alertType}/page/{page}/items/{size}")    
    public List<Alert> getAllEventsByUserAndAlertType(@PathVariable("id") String id, 
                                    @PathVariable("alertType") String alertType,
                                    @PathVariable("page") String page,
                                    @PathVariable("size") String size, 
                                    HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        List<Alert> res = alertRepository.findByRefUserAndAlertTypeOrderByDateTimeDesc(id, alertType, pageable);
        return res;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/type/{alertType}/subtype/{subtype}/page/{page}/items/{size}")    
    public List<Alert> getAllEventsByUserAndAlertTypeAndSubtype(@PathVariable("id") String id, 
                                    @PathVariable("alertType") String alertType,
                                    @PathVariable("subtype") String subtype,
                                    @PathVariable("page") String page,
                                    @PathVariable("size") String size, 
                                    HttpServletRequest request) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        List<Alert> res = alertRepository.findByRefUserAndAlertTypeAndSubtypeAlertOrderByDateTimeDesc(id, alertType, subtype, pageable);
        return res;
    }
    
//    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/date/{date}/page/{page}/items/{size}")    
//    public List<Alert> getAllEventsByUserAndDate(@PathVariable("id") String id, 
//                                    @PathVariable("date") Date date, 
//                                    @PathVariable("page") String page,
//                                    @PathVariable("size") String size, 
//                                    HttpServletRequest request) {
//        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
//        List<Alert> res = alertRepository.findByRefUserAndDateTimeOrderByDateTimeDesc(id, date, pageable);
//        return res;
//    }
//    
//    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/type/{alertType}/date/{date}/page/{page}/items/{size}")    
//    public List<Alert> getAllEventsByUserAndAlertTypeAndDate(@PathVariable("id") String id, 
//                                    @PathVariable("date") Date date, 
//                                    @PathVariable("alertType") String alertType,
//                                    @PathVariable("page") String page,
//                                    @PathVariable("size") String size, 
//                                    HttpServletRequest request) {
//        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
//        List<Alert> res = alertRepository.findByRefUserAndAlertTypeAndDateTimeOrderByDateTimeDesc(id, alertType, date, pageable);
//        return res;
//    }
//    
//    @RequestMapping(method = RequestMethod.GET, value = "/my-events/{id}/type/{alertType}/subtype/{subtype}/date/{date}/page/{page}/items/{size}")    
//    public List<Alert> getAllEventsByUserAndAlertTypeAndSubtypeAndDate(@PathVariable("id") String id, 
//                                    @PathVariable("date") Date date, 
//                                    @PathVariable("alertType") String alertType,
//                                    @PathVariable("subtype") String subtype,
//                                    @PathVariable("page") String page,
//                                    @PathVariable("size") String size, 
//                                    HttpServletRequest request) {
//        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
//        List<Alert> res = alertRepository.findByRefUserAndAlertTypeAndSubtypeAlertAndDateTimeOrderByDateTimeDesc(id, alertType, subtype, date, pageable);
//        return res;
//    }
}
