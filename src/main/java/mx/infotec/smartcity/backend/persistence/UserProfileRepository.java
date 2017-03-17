package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author Erik Valdivieso
 */
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    
    UserProfile findByEmail(String email);
    List<UserProfile> findByGroup(String group);
    
}
