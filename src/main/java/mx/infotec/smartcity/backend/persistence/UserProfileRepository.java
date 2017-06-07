package mx.infotec.smartcity.backend.persistence;

import java.util.List;
import mx.infotec.smartcity.backend.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Erik Valdivieso
 */
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
    
    UserProfile findByKeystoneId(String keystoneId);
    UserProfile findByEmail(String email);
    List<UserProfile> findByGroup(String group);
    
    @Query(value = "{'groups.$id': ?0}")
    List<UserProfile> findByGroupID(int groupID);
    
    
    
}
