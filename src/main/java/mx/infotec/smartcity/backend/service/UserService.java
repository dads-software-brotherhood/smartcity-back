package mx.infotec.smartcity.backend.service;

import java.io.Serializable;
import java.util.List;

import mx.infotec.smartcity.backend.service.keystone.pojo.Group;
import mx.infotec.smartcity.backend.service.keystone.pojo.Project;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.User;

/**
 *
 * @author Erik Valdivieso
 */
public interface UserService extends Serializable {

  public List<User> getAllUsers(String authToken);

  public CreateUser createUser(CreateUser user, String authToken);

  User getUser(String idUser, String authToken);

  User updateUser(String idUser, String authToken);

  boolean deleteUser(String idUser, String authToken);

  Project getUserProjects(String idUser, String authToken);

  boolean changePassword(String idUser, String authToken, String password);

  Group getUserGroups(String idUser, String authToken);

}
