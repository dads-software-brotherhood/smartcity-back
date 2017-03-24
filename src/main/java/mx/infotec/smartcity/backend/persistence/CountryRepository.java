package mx.infotec.smartcity.backend.persistence;

import mx.infotec.smartcity.backend.model.Country;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Erik Valdivieso
 */
public interface CountryRepository extends MongoRepository<Country, Integer> {
    
    Country findByCountryCode(String countryCode);
}
