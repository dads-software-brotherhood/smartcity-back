package mx.infotec.smartcity.backend.service.keystone;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.keystone.pojo.Request;
import mx.infotec.smartcity.backend.service.keystone.pojo.changePassword.ChangeUserPassword;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.token.Token;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.User;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.Users;
import mx.infotec.smartcity.backend.utils.Constants;


/**
 *
 * @author Benjamin Vander Stichelen
 */
@Service("keystoneUserService")
public class KeystoneUserServiceImpl implements UserService {

  private static final long   serialVersionUID = 1L;

  private static final Logger LOGGER           =
      LoggerFactory.getLogger(KeystoneUserServiceImpl.class);


  @Value("${idm.servers.keystone}")
  private String              keystonUrl;

  private String              userUrl;
  private String              changePasswordUrl;
  private String              updateUserUrl;
  private String              tokenUrl;
  private String              DBLCUOTE         = "\"";
  private Integer             TIMEOUT          = 90000;
  private String              paramName;

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
  public List<User> getAllUsers(String authToken) {
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
  public CreateUser createUser(CreateUser user, String authToken) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", authToken);
    HttpEntity<CreateUser> requestEntity = new HttpEntity<CreateUser>(user, headers);
    HttpEntity<CreateUser> responseEntity =
        restTemplate.exchange(userUrl, HttpMethod.POST, requestEntity, CreateUser.class);
    LOGGER.info("user url: en df create user {} ", userUrl);
    return responseEntity.getBody();

  }


  @Override
  public CreateUser updateUser(String idUser, String authToken, CreateUser user) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();
    requestFactory.setConnectTimeout(TIMEOUT);
    requestFactory.setReadTimeout(TIMEOUT);

    restTemplate.setRequestFactory(requestFactory);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", authToken);
    HttpEntity<CreateUser> requestEntity = new HttpEntity<CreateUser>(user, headers);
    HttpEntity<CreateUser> responseEntity = restTemplate.exchange(
        String.format(updateUserUrl, idUser), HttpMethod.PATCH, requestEntity, CreateUser.class);
    return responseEntity.getBody();

  }

  @Override
  public CreateUser deleteUser(String userId, String authToken) {
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
  public Object changePassword(String userid, ChangeUserPassword user, String authToken) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", authToken);
    HttpEntity<ChangeUserPassword> requestEntity = new HttpEntity<>(user, headers);
    LOGGER.info("user url: {}", String.format(changePasswordUrl, userid));
    HttpEntity<ChangeUserPassword> responseEntity =
        restTemplate.exchange(String.format(changePasswordUrl, userid), HttpMethod.PATCH,
            requestEntity, ChangeUserPassword.class);
    return responseEntity.getBody();


  }

  @Override
  public CreateUser getUser(String userId, String authToken) {
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
  public User getUserByName(String name, String authToken) {
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
  public CreateUser getUserByUsername(String username, String authToken) {
    List<User> users = this.getAllUsers(authToken);
    for (User user : users) {
      if (user.getName().equals(username)) {
        return getUser(user.getId(), authToken);
      }
    }
    return new CreateUser();
  }

  @Override
  public Token getUserFromToken(String tokenAdmin, String authToken) {
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

}
