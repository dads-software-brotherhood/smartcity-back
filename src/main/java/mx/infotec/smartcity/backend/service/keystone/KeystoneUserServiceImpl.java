package mx.infotec.smartcity.backend.service.keystone;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.infotec.smartcity.backend.model.Email;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.model.TokenInfo;
import mx.infotec.smartcity.backend.model.TokenRecovery;
import mx.infotec.smartcity.backend.model.TokenType;
import mx.infotec.smartcity.backend.model.UserModel;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.Request;
import mx.infotec.smartcity.backend.service.keystone.pojo.changePassword.ChangeUserPassword;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.User_;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Roles;
import mx.infotec.smartcity.backend.service.keystone.pojo.token.Token;
import mx.infotec.smartcity.backend.service.keystone.pojo.token.Token_;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.User;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.Users;
import mx.infotec.smartcity.backend.service.mail.MailService;
import mx.infotec.smartcity.backend.service.recovery.TokenRecoveryService;
import mx.infotec.smartcity.backend.utils.Constants;
import mx.infotec.smartcity.backend.utils.RoleUtil;
import mx.infotec.smartcity.backend.utils.TemplatesEnum;


/**
 *
 * @author Benjamin Vander Stichelen
 */
@Service("keystoneUserService")
public class KeystoneUserServiceImpl implements UserService {

  private static final long     serialVersionUID = 1L;

  private static final Logger   LOGGER           =
      LoggerFactory.getLogger(KeystoneUserServiceImpl.class);

  @Value("${idm.admin.username}")
  private String                idmUsr;

  @Autowired
  private AdminUtilsService     adminUtils;

  @Autowired
  private MailService           mailService;

  @Autowired
  private TokenRecoveryService  recoveryService;

  @Autowired
  @Qualifier("keystoneRoleService")
  private RoleService           roleService;

  @Autowired
  private UserProfileRepository userRepository;
  
  @Autowired
  @Qualifier("keystoneLoginService")
  private LoginService loginService;


  @Value("${idm.servers.keystone}")
  private String                keystonUrl;

  private String                userUrl;
  private String                changePasswordUrl;
  private String                updateUserUrl;
  private String                tokenUrl;
  private String                DBLCUOTE         = "\"";
  private Integer               TIMEOUT          = 90000;
  private String                paramName;

  @PostConstruct
  protected void init() {
    userUrl = keystonUrl + "/v3/users";
    changePasswordUrl = userUrl + "/%s/password";
    updateUserUrl = userUrl + "/%s";
    tokenUrl = keystonUrl + "/v3/auth/tokens";
    paramName = userUrl + "?name=%s";
    LOGGER.info("user url: {}", userUrl);
  }

  @Override
  public List<User> getAllUsers(String authToken) throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    Request request = new Request();
    HttpHeaders headers = new HttpHeaders();
    // headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", authToken);
    HttpEntity<Request> requestEntity = new HttpEntity<Request>(request, headers);
    HttpEntity<Users> responseEntity =
        restTemplate.exchange(userUrl, HttpMethod.GET, requestEntity, Users.class);

    return responseEntity.getBody().getUsers();
  }



  @Override
  public boolean createUser(CreateUser user) throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      String tokenAdmin = adminUtils.getAdmintoken();
      headers.set(Constants.AUTH_TOKEN_HEADER, tokenAdmin);
      HttpEntity<CreateUser> requestEntity = new HttpEntity<CreateUser>(user, headers);
      restTemplate.exchange(userUrl, HttpMethod.POST, requestEntity, CreateUser.class);
      return true;
    } catch (Exception e) {
      LOGGER.error("Error to create user, cause: ", e);
      throw new ServiceException(e);
    }
  }

  @Override
  public CreateUser createUserWithRole(CreateUser user, Role role) throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    try {
      String tokenAdmin = adminUtils.getAdmintoken();
      headers.set(Constants.AUTH_TOKEN_HEADER, tokenAdmin);
      HttpEntity<CreateUser> requestEntity = new HttpEntity<CreateUser>(user, headers);
      HttpEntity<CreateUser> httpCreatedUser =
          restTemplate.exchange(userUrl, HttpMethod.POST, requestEntity, CreateUser.class);
      CreateUser createdUser = httpCreatedUser.getBody();
      String userId = createdUser.getUser().getId();
      String adminToken = this.adminUtils.getAdmintoken();
      roleService.assignRoleToUserOnDefaultDomain(RoleUtil.getInstance().getIdRole(role), userId,
          adminToken);
      return createdUser;
    } catch (Exception e) {
      LOGGER.error("Error to create user, cause: ", e);
      throw new ServiceException(e);
    }
  }


  @Override
  public CreateUser updateUser(String idUser, String authToken, CreateUser user)
      throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();
    requestFactory.setConnectTimeout(TIMEOUT);
    requestFactory.setReadTimeout(TIMEOUT);

    restTemplate.setRequestFactory(requestFactory);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set(Constants.AUTH_TOKEN_HEADER, authToken);
    HttpEntity<CreateUser> requestEntity = new HttpEntity<CreateUser>(user, headers);
    HttpEntity<CreateUser> responseEntity = restTemplate.exchange(
        String.format(updateUserUrl, idUser), HttpMethod.PATCH, requestEntity, CreateUser.class);
    return responseEntity.getBody();

  }

  @Override
  public CreateUser deleteUser(String userId, String authToken) throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", authToken);
    LOGGER.info("user url: {}", String.format(updateUserUrl, userId));
    HttpEntity<CreateUser> requestEntity = new HttpEntity<CreateUser>(headers);
    HttpEntity<CreateUser> responseEntity = restTemplate.exchange(
        String.format(updateUserUrl, userId), HttpMethod.DELETE, requestEntity, CreateUser.class);
    return responseEntity.getBody();

  }



  @Override
  public Object changePassword(String userid, ChangeUserPassword user, String authToken)
      throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", authToken);
    HttpEntity<ChangeUserPassword> requestEntity = new HttpEntity<>(user, headers);
    System.out.println(requestEntity.getBody());
    LOGGER.info("user url: {}", String.format(changePasswordUrl, userid));
    HttpEntity<ChangeUserPassword> responseEntity =
        restTemplate.exchange(String.format(changePasswordUrl, userid), HttpMethod.POST,
            requestEntity, ChangeUserPassword.class);
    return responseEntity.getBody();


  }

  @Override
  public CreateUser getUser(String userId, String authToken) throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", authToken);
    LOGGER.info("user url: {}", String.format(updateUserUrl, userId));
    HttpEntity<CreateUser> requestEntity = new HttpEntity<CreateUser>(headers);
    HttpEntity<CreateUser> responseEntity = restTemplate.exchange(
        String.format(updateUserUrl, userId), HttpMethod.GET, requestEntity, CreateUser.class);
    return responseEntity.getBody();
  }

  @Override
  public User getUserByName(String name, String authToken) throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    Request request = new Request();
    HttpHeaders headers = new HttpHeaders();
    headers.add(Constants.AUTH_TOKEN_HEADER, authToken);
    HttpEntity<Request> requestEntity = new HttpEntity<Request>(request, headers);
    HttpEntity<Users> responseEntity = restTemplate.exchange(String.format(paramName, name),
        HttpMethod.GET, requestEntity, Users.class);
    if (!responseEntity.getBody().getUsers().isEmpty()) {
      return responseEntity.getBody().getUsers().get(0);
    } else {
      return null;
    }

  }

  @Override
  public CreateUser getUserByUsername(String username, String authToken) throws ServiceException {
    List<User> users = this.getAllUsers(authToken);
    for (User user : users) {
      if (user.getName().equals(username)) {
        return getUser(user.getId(), authToken);
      }
    }
    return new CreateUser();
  }

  @Override
  public Token getUserFromToken(String tokenAdmin, String authToken) throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", tokenAdmin);
    headers.set("X-Subject-Token", authToken);
    HttpEntity<Token> requestEntity = new HttpEntity<Token>(headers);
    HttpEntity<Token> responseEntity =
        restTemplate.exchange(tokenUrl, HttpMethod.GET, requestEntity, Token.class);
    return responseEntity.getBody();
  }

  @Override
  public IdentityUser getUserFromTokenToIdentityUser(String tokenAdmin, String authToken)
      throws ServiceException {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", tokenAdmin);
    headers.set("X-Subject-Token", authToken);
    HttpEntity<Token> requestEntity = new HttpEntity<Token>(headers);
    HttpEntity<Token> responseEntity =
        restTemplate.exchange(tokenUrl, HttpMethod.GET, requestEntity, Token.class);
    Token token = responseEntity.getBody();

    return convertTokenToIdentity(token, tokenAdmin, authToken);
  }

  private IdentityUser convertTokenToIdentity(Token tokenM, String tokenAdmin, String authToken) {
    IdentityUser idu = new IdentityUser();
    TokenInfo tokenInfo = new TokenInfo();
    if (tokenM.getToken() != null) {
      Token_ token = tokenM.getToken();
      if (token.getUser() != null) {
        // idu.setName(token.getUser().getName());
        idu.setId(token.getUser().getId());
        idu.setUsername(token.getUser().getName());

      }
      tokenInfo.setStart(token.getIssuedAt());
      tokenInfo.setEnd(token.getExpiresAt());
      // tokenInfo.setEnd(ISO8601DateParser.parse(token.getExpiresAt()));
      // tokenInfo.setStart(ISO8601DateParser.parse(token.getIssuedAt()));
      tokenInfo.setTokenType(TokenType.OTHER);
      tokenInfo.setToken(authToken);


      Set<Role> rolesEnum = new HashSet<>();
      if (token.getRoles() != null && token.getRoles().size() > 0) {
        Set<Role> roles = convertRoles(token.getRoles());
        rolesEnum.addAll(roles);
      }
      Set<Role> roles2 = convertRolesFromRoles(
          this.roleService.getRoleUserDefaultDomain(idu.getId(), tokenAdmin).getRoles());

      rolesEnum.addAll(roles2);
      idu.setRoles(rolesEnum);
      idu.setTokenInfo(tokenInfo);
    }
    return idu;
  }

  private Set<Role> convertRolesFromRoles(
      List<mx.infotec.smartcity.backend.service.keystone.pojo.roles.Role> roles) {
    Set<Role> rolesEnum = new HashSet<>();
    // rolesEnum.add(Role.ADMIN);
    for (mx.infotec.smartcity.backend.service.keystone.pojo.roles.Role role : roles) {
      Role roleEnum = RoleUtil.validateRole(role.getName());
      if (roleEnum != null) {
        rolesEnum.add(roleEnum);
      }
    }
    return rolesEnum;

  }

  private Set<Role> convertRoles(
      List<mx.infotec.smartcity.backend.service.keystone.pojo.token.Role> roles) {
    Set<Role> rolesEnum = new HashSet<>();
    for (mx.infotec.smartcity.backend.service.keystone.pojo.token.Role role : roles) {
      Role roleEnum = RoleUtil.validateRole(role.getName());
      if (roleEnum != null) {
        rolesEnum.add(roleEnum);
      }
    }
    return rolesEnum;
  }

  @Override
  public boolean isRegisteredUser(String name) throws ServiceException {
    try {
      String tokenAdmin = adminUtils.getAdmintoken();
      User registeredUser = getUserByName(name, tokenAdmin);
      if (registeredUser != null && registeredUser.getName().equals(name)) {
        return true;
      }
      return false;
    } catch (Exception e) {
      LOGGER.error("Error until validate a registered user, cause: ", e);
      throw new ServiceException(e);
    }

  }

  @Override
  public boolean createUserAndSendMail(CreateUser user, TemplatesEnum template)
      throws ServiceException {
    try {
      createUserWithRole(user, Role.USER);
      TokenRecovery recovery =
          recoveryService.generateToken(user.getUser().getName(), user.getUser().getId());
      LOGGER.info("Create User token: " + recovery.getId());
      Email email = new Email();
      email.setTo(user.getUser().getName());
      email.setMessage(recovery.getId());
      mailService.sendMail(template, email);
      return true;
    } catch (Exception e) {
      LOGGER.error("Error to create user and send notificartion, cause: ", e);
    }

    return false;
  }

  @Override
  public boolean createUserByAdmin(UserModel userModel) throws ServiceException {
    User_ user = new User_();
    user.setName(userModel.getEmail());
    user.setPassword("");
    CreateUser createUser = new CreateUser(user);
    try {
      CreateUser createdUser = createUserWithRole(createUser, userModel.getRole());
      UserProfile userProfile = new UserProfile();
      userProfile.setEmail(createUser.getUser().getName());
      userProfile.setName(userModel.getName());
      userProfile.setFamilyName(userModel.getFamilyName());
      userProfile.setRegisterDate(new Date());
      userProfile.setKeystoneId(createdUser.getUser().getId());
      userRepository.save(userProfile);
      TokenRecovery recovery = recoveryService.generateToken(createUser.getUser().getName(),
          createUser.getUser().getId());
      LOGGER.info("Token recovery: " + recovery.getId());
      Email email = new Email();
      email.setTo(createUser.getUser().getName());
      email.setMessage(recovery.getId());
      mailService.sendMail(TemplatesEnum.MAIL_SAMPLE, email);
      return true;
    } catch (Exception e) {
      LOGGER.error("Error trying to create user by admin, cause: ", e);
    }

    return false;
  }

  @Override
  public List<UserModel> getUserModelList() throws ServiceException {
    try {
      List<UserProfile> usersProfileList = userRepository.findAll();
      String tokenAdmin = adminUtils.getAdmintoken();
      if (usersProfileList != null && !usersProfileList.isEmpty()) {
        List<UserModel> usersModelList = new ArrayList<>();
        for (UserProfile item : usersProfileList) {
          if (item.getName().equals(idmUsr)) {
            continue;
          }
          UserModel model = new UserModel();
          model.setEmail(item.getEmail());
          model.setFamilyName(item.getFamilyName());
          model.setName(item.getName());
          Roles rolesByUser =
              roleService.getRoleUserDefaultDomain(item.getKeystoneId(), tokenAdmin);
          if (rolesByUser == null || item.getKeystoneId() == null) {
            model.setRole(null);
          } else {
            model.setRole(RoleUtil.getInstance()
                .validateRole(rolesByUser.getRoles().get(0).getName().toUpperCase()));
          }

          usersModelList.add(model);
        }
        return usersModelList;
      }
      return null;
    } catch (Exception e) {
      LOGGER.error("Error on usersProfile recovery, cause: ", e);
      throw new ServiceException(e);
    }

  }

  @Override
  public boolean deleteUserByAdmin(UserModel model) throws ServiceException {
    try {
      String adminToken = adminUtils.getAdmintoken();
      UserProfile profile = userRepository.findByEmail(model.getEmail());
      deleteUser(profile.getKeystoneId(), adminToken);
      userRepository.delete(profile.getId());
      return true;
    } catch (Exception e) {
      LOGGER.error("Error trying delete user, cause: ", e);
      throw new ServiceException(e);
    }
  }

  @Override
  public boolean changePassword(
      mx.infotec.smartcity.backend.service.keystone.pojo.changePassword.User_ user, String token)
      throws ServiceException {
    try {
      ChangeUserPassword password = new ChangeUserPassword();
      password.setUser(user);
      IdentityUser identity = loginService.findUserByValidToken(token);
      Object obj = changePassword(identity.getId(), password, token);
      System.out.println(obj);
      return true;
    } catch (Exception e) {
      LOGGER.error("Error to trying update password, cause: ", e);
      throw new ServiceException(e);
    }
   
  }

  @Override
  public List<UserModel> filterUsers(UserModel model) throws ServiceException {
    // TODO Auto-generated method stub
    if (model.getRole() != null) {
      
    } else {
      
    }
    return null;
  }

}
