package mx.infotec.smartcity.backend.controller;

import java.util.List;
import javax.websocket.server.PathParam;
import mx.infotec.smartcity.backend.CustomerRepository;
import mx.infotec.smartcity.backend.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author erik
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @RequestMapping(method = RequestMethod.GET, value = "/count")
    public long count() {
        return customerRepository.count();
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Customer getById(@PathParam("id") String id) {
        return customerRepository.findOne(id);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public void deleteByID(@PathParam("id") String id) {
        customerRepository.delete(id);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Customer customer) {
        if (customer.getId() != null) {
            LOGGER.warn("ID is not null, perform update");
        }
        return ResponseEntity.accepted().body(customerRepository.save(customer));
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Customer customer) {
        return ResponseEntity.accepted().body(customerRepository.save(customer));
    }
}
