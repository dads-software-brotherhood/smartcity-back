package mx.infotec.smartcity.backend.persistence;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import mx.infotec.smartcity.backend.model.Group;



/**
 *
 * @author Benjamin Vander Stichelen
 */
public interface GroupRepository extends MongoRepository<Group, Integer> {

  List<Group> findAllById(List<String> listId, Pageable pageable);

  List<Group> findAllById(List<String> listId);

  Group findById(String id);
  
  Group findByGroup(String name);
  
  Group findFirstByOrderByIdDesc();



}
