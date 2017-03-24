package mx.infotec.smartcity.backend.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.infotec.smartcity.backend.model.UserProfile;

/**
 *
 * @author Erik Valdivieso
 */
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

  UserProfile findByEmail(String email);

  List<UserProfile> findByGroup(String group);

}
