package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.List;
import mx.infotec.smartcity.backend.model.Region;
import mx.infotec.smartcity.backend.persistence.RegionRepository;
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
@RequestMapping("/regions")
public class RegionController {
    
    @Autowired
    private RegionRepository regionRepository;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Region> getAll(@RequestParam(value = "countryId", required = false) Integer countryId) {
        List<Region> res;
        
        if (countryId == null) {
            res = regionRepository.findAll();
        } else {
            res = regionRepository.findByCountryId(countryId);
        }
        
        if (res == null) {
            return new ArrayList<>();
        } else {
            return res;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Region getById(@PathVariable int id) {
        return regionRepository.findOne(id);
    }
}
