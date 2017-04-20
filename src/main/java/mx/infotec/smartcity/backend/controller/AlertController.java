package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.List;
import mx.infotec.smartcity.backend.model.Alert;
import mx.infotec.smartcity.backend.persistence.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    
    private int                 SIZE   = 5;
    
    @RequestMapping(method = RequestMethod.GET)    
    public List<Alert> getAll() {
        List<Alert> res = alertRepository.findAllByOrderByDataTimeDesc();
        
        if (res == null) {
            return new ArrayList<>();
        } else {
            return res;
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/page/{page}/{size}")
    public Page<Alert> getByPageSize(@PathVariable("page") String page,
    @PathVariable("size") String size) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));
        return alertRepository.findAll(pageable);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/page/{page}")
    public Page<Alert> getByPage(@PathVariable("page") String page,
    @PathVariable("size") String size) {
        Pageable pageable = new PageRequest(Integer.parseInt(page), SIZE);
        return alertRepository.findAll(pageable);
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Alert getById(@PathVariable int id) {
        return alertRepository.findOne(id);
    }
    
}
