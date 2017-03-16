package mx.infotec.smartcity.backend.controller;

import java.util.List;
import mx.infotec.smartcity.backend.CustomerRepository;
import mx.infotec.smartcity.backend.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public List<Customer> getAll(@RequestHeader HttpHeaders headers) {
        LOGGER.debug("Headers: {}", headers);

        return customerRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Customer getById(@PathVariable String id) {
        return customerRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable String id) {
        try {
            customerRepository.delete(id);
            return ResponseEntity.accepted().body("deleted");
        } catch (Exception ex) {
            LOGGER.error("Error at delete", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Customer customer) {
        if (customer.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID must be null");
        } else {
            try {
                return ResponseEntity.accepted().body(customerRepository.insert(customer));
            } catch (Exception ex) {
                LOGGER.error("Error at insert", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> update(@RequestBody Customer customer, @PathVariable("id") String id) {
        try {
            if (customerRepository.exists(id)) {
                if (customer.getId() != null) {
                    LOGGER.warn("ID from object is ignored");
                }

                customer.setId(id);
                customerRepository.save(customer);
                
                return ResponseEntity.accepted().body("updated");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID don't exists");
            }
        } catch (Exception ex) {
            LOGGER.error("Error at update", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }

    }
}
