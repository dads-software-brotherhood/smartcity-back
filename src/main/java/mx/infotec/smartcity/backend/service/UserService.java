package mx.infotec.smartcity.backend.service;

import java.io.Serializable;
import java.util.List;

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.model.UserModel;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.changePassword.ChangeUserPassword;
import mx.infotec.smartcity.backend.service.keystone.pojo.changePassword.User_;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.token.Token;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.User;
import mx.infotec.smartcity.backend.utils.TemplatesEnum;

/**
 *
 * @author Erik Valdivieso
 */
public interface UserService extends Serializable {

  // Get All users
  public List<User> getAllUsers(String authToken) throws ServiceException;

  // Create a user
  public boolean createUser(CreateUser user) throws ServiceException;

  // Lookup user with id
  CreateUser getUser(String idUser, String authToken) throws ServiceException;

  // Update user
  CreateUser updateUser(String idUser, String authToken, CreateUser user) throws ServiceException;

  // Delete a user with id
  CreateUser deleteUser(String idUser, String authToken) throws ServiceException;

  // Change password of user having the original password
  public Object changePassword(String userid, ChangeUserPassword user, String token)
      throws ServiceException;

  // Search user with name
  User getUserByName(String name, String authToken) throws ServiceException;

  // Search user with username
  CreateUser getUserByUsername(String username, String authToken) throws ServiceException;

  /**
   * Method to look up the user who is in the to authToken with a admin token
   * 
   * @param tokenAdmin
   * @param authToken
   * @return JsonToken
   * @throws mx.infotec.smartcity.backend.service.exception.ServiceException
   */
  Token getUserFromToken(String tokenAdmin, String authToken) throws ServiceException;

  boolean isRegisteredUser(String name) throws ServiceException;

  boolean createUserAndSendMail(CreateUser user, TemplatesEnum template) throws ServiceException;

  IdentityUser getUserFromTokenToIdentityUser(String tokenAdmin, String authToken)
      throws ServiceException;


  CreateUser createUserWithRole(CreateUser user, Role role) throws ServiceException;
  
  boolean createUserByAdmin(UserModel userModel) throws ServiceException;
  
  List<UserModel> getUserModelList() throws ServiceException;
  
  boolean deleteUserByAdmin(UserModel model) throws ServiceException;
  
  boolean changePassword(User_ user, String token) throws ServiceException;

  List<UserModel> filterUsers(UserModel model) throws ServiceException;
}
