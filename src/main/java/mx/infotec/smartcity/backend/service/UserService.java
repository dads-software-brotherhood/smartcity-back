package mx.infotec.smartcity.backend.service;

import java.io.Serializable;
import java.util.List;

import mx.infotec.smartcity.backend.service.keystone.pojo.Group;
import mx.infotec.smartcity.backend.service.keystone.pojo.Project;
import mx.infotec.smartcity.backend.service.keystone.pojo.changePassword.ChangeUserPassword;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.User;

/**
 *
 * @author Erik Valdivieso
 */
public interface UserService extends Serializable {

  // Get All users
  public List<User> getAllUsers(String authToken);

  // Create a user
  public CreateUser createUser(CreateUser user, String authToken);

  // Lookup user with id
  CreateUser getUser(String idUser, String authToken);

  // Update user
  CreateUser updateUser(String idUser, String authToken, CreateUser user);

  // Delete a user with id
  CreateUser deleteUser(String idUser, String authToken);

  // Get Project of user
  Project getUserProjects(String idUser, String authToken);

  // Get Group of user
  Group getUserGroups(String idUser, String authToken);

  // Change password of user having the original password
  public Object changePassword(String userid, ChangeUserPassword user, String token);

  // Search user with name
  CreateUser getUserByName(String name, String authToken);

  // Search user with username
  CreateUser getUserByUsername(String username, String authToken);

}
