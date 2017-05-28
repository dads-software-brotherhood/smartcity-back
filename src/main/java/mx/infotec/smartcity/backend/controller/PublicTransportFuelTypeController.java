package mx.infotec.smartcity.backend.controller;

import java.util.List;
import mx.infotec.smartcity.backend.model.transport.PublicTransportFuelType;
import mx.infotec.smartcity.backend.persistence.PublicTransportFuelTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Infotec
 */
@RestController
@RequestMapping("/public-transport-fuel-type")
public class PublicTransportFuelTypeController {
    
    @Autowired
    private PublicTransportFuelTypeRepository publicTransportFuelTypeRepository;
    
    private final Sort defaultSort;
    
    public PublicTransportFuelTypeController() {
        this.defaultSort = new Sort(new Sort.Order("name"));
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<PublicTransportFuelType> getAll() {
        return publicTransportFuelTypeRepository.findAll(defaultSort);
    }
    
}
