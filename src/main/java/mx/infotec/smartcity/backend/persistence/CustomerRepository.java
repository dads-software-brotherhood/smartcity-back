package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Erik Valdivieso
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    List<Customer> findByFirstName(String firstName);
    List<Customer> findByLastName(String lastName);

}
