package mx.infotec.smartcity.backend.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import mx.infotec.smartcity.backend.model.TokenRecovery;

public interface TokenRecoveryRepository extends MongoRepository<TokenRecovery, String> {    
    
    /**
     * Delete all entries with email
     * @param email
     * @return 
     */
    Long deleteTokenRecoveryByEmail(String email);
}
