package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.List;
import mx.infotec.smartcity.backend.model.Locality;
import mx.infotec.smartcity.backend.persistence.LocalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Erik Valdivieso
 */
@RestController
@RequestMapping("/localities")
public class LocalityController {
    
    @Autowired
    private LocalityRepository localityRepository;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Locality> getAll(@RequestParam(value = "regionId", required = false) Integer regionId) {
        List<Locality> res;
        
        if (regionId == null) {
            res = localityRepository.findAll();
        } else {
            res = localityRepository.findByRegionId(regionId);
        }
        
        if (res == null) {
            return new ArrayList<>();
        } else {
            return res;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Locality getById(@PathVariable int id) {
        return localityRepository.findOne(id);
    }
}
