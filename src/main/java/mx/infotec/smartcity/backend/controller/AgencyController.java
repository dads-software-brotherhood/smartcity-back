package mx.infotec.smartcity.backend.controller;

import java.util.List;
import mx.infotec.smartcity.backend.model.transport.Agency;
import mx.infotec.smartcity.backend.persistence.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Infotec
 */
@RestController
@RequestMapping("/agency")
public class AgencyController {

    @Autowired
    private AgencyRepository agencyRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Agency> getAll() {
        return agencyRepository.findAll();
    }
    
}
