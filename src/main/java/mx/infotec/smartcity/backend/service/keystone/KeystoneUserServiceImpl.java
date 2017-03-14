package mx.infotec.smartcity.backend.service.keystone;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Token;
import mx.infotec.smartcity.backend.model.TokenType;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.keystone.pojo.Group;
import mx.infotec.smartcity.backend.service.keystone.pojo.Project;
import mx.infotec.smartcity.backend.service.keystone.pojo.Request;
import mx.infotec.smartcity.backend.service.keystone.pojo.Response;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.User;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.Users;


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

  private String              DBLCUOTE         = "\"";

  @PostConstruct
  protected void init() {
    userUrl = keystonUrl + "/v3/users";
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
    HttpEntity<Request> requestEntity = new HttpEntity(request, headers);
    HttpEntity<Users> responseEntity =
        restTemplate.exchange(userUrl, HttpMethod.GET, requestEntity, Users.class);

    return responseEntity.getBody().getUsers();
  }

  private List<User> convertUsers(HttpEntity<Users> responseEntity) {
    Users users = responseEntity.getBody();
    HttpHeaders headers = responseEntity.getHeaders();

    return users.getUsers();
  }

  private IdentityUser convertUser(Response response, HttpHeaders headers) {
    if (response.getToken() == null && response.getToken().getUser() == null) {
      return null;
    } else {
      IdentityUser idmUser = new IdentityUser();

      if (response.getToken().getRoles() != null) {
        Set<String> roles = new HashSet<>();

        response.getToken().getRoles().forEach((role) -> {
          roles.add(role.getName());
        });

        idmUser.setRoles(roles);
      }

      Token token = new Token();

      token.setTokenType(TokenType.OTHER);
      token.setStart(response.getToken().getIssuedAt());
      token.setEnd(response.getToken().getExpiresAt());

      if (token.getStart() != null && token.getEnd() != null) {
        long tmp = token.getEnd().getTime() - token.getStart().getTime();
        token.setTime((int) tmp / 1000);
      }

      List<String> tmp = headers.get("x-subject-token");

      if (!tmp.isEmpty()) {
        token.setToken(tmp.get(0));
      }

      idmUser.setToken(token);

      idmUser.setUsername(response.getToken().getUser().getName());

      return idmUser;
    }

  }

  @Override
  public CreateUser createUser(CreateUser user, String authToken) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("X-auth-token", authToken);
    StringBuffer strbfr = new StringBuffer();

    HttpEntity<CreateUser> requestEntity = new HttpEntity(user, headers);
    HttpEntity<CreateUser> responseEntity =
        restTemplate.exchange(userUrl, HttpMethod.POST, requestEntity, CreateUser.class);
    LOGGER.info("user url: en create user{}", userUrl);
    return responseEntity.getBody();

  }

  @Override
  public User getUser(String idUser, String authToken) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User updateUser(String idUser, String authToken) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean deleteUser(String idUser, String authToken) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Group getUserGroups(String idUser, String authToken) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Project getUserProjects(String idUser, String authToken) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean changePassword(String idUser, String authToken, String password) {
    // TODO Auto-generated method stub
    return false;
  }

}
