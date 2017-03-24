package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.List;
import mx.infotec.smartcity.backend.model.Country;
import mx.infotec.smartcity.backend.persistence.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Erik Valdivieso
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryRepository countryRepository;
    
    @RequestMapping(method = RequestMethod.GET)    
    public List<Country> getAll() {
        List<Country> res = countryRepository.findAll();
        
        if (res == null) {
            return new ArrayList<>();
        } else {
            return res;
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Country getById(@PathVariable int id) {
        return countryRepository.findOne(id);
    }
    
}
