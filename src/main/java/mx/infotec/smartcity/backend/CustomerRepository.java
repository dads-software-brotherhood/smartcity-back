package mx.infotec.smartcity.backend;

import java.util.List;
import mx.infotec.smartcity.backend.model.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author erik
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}