package mx.infotec.smartcity.backend.service.keystone;

import java.util.ArrayList;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.model.TokenInfo;
import mx.infotec.smartcity.backend.model.TokenType;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.Auth;
import mx.infotec.smartcity.backend.service.keystone.pojo.Identity;
import mx.infotec.smartcity.backend.service.keystone.pojo.Request;
import mx.infotec.smartcity.backend.service.keystone.pojo.Response;
import mx.infotec.smartcity.backend.service.keystone.pojo.User;
import mx.infotec.smartcity.backend.service.keystone.pojo.token.Token_;
import mx.infotec.smartcity.backend.utils.Constants;
import mx.infotec.smartcity.backend.utils.RoleUtil;

/**
 *
 * @author Erik Valdivieso
 */
@Service("keystoneLoginService")
public class KeystoneLoginServiceImpl implements LoginService {

  private static final long   serialVersionUID = 1L;

  private static final Logger LOGGER           =
      LoggerFactory.getLogger(KeystoneLoginServiceImpl.class);

  @Autowired
  @Qualifier("adminUtils")
  private AdminUtilsService   adminUtils;

  @Autowired
  @Qualifier("keystoneRoleService")
  private RoleService         roleService;
  @Autowired
  @Qualifier("keystoneUserService")
  private UserService         userService;

  @Value("${idm.servers.keystone}")
  private String              keystonUrl;

  @Value("${idm.default.domain}")
  private String              defaultDomain;

  @Value("${idm.admin.username}")
  private String              adminUser;

  private String              tokenRequestUrl;

  @PostConstruct
  protected void init() {
    tokenRequestUrl = keystonUrl + "/v3/auth/tokens";

    LOGGER.info("Token url: {}", tokenRequestUrl);
  }

  @Override
  public IdentityUser performLogin(String username, char[] password)
      throws InvalidCredentialsException {
    try {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

      Request request = new Request(new User(username, new String(password)));
      HttpEntity<Request> requestEntity = new HttpEntity<>(request);
      HttpEntity<Response> responseEntity =
          restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, requestEntity, Response.class);

      return convert(responseEntity);
    } catch (Exception ex) {
      throw new InvalidCredentialsException(ex);
    }
  }

  @Override
  public IdentityUser findUserByValidToken(String token)
      throws InvalidTokenException, ServiceException {
    if (isValidToken(token)) {
      String tokenAdmin = adminUtils.getAdmintoken();
      IdentityUser idu = userService.getUserFromTokenToIdentityUser(tokenAdmin, token);
      this.invalidToken(tokenAdmin);
      return idu;
    } else {
      return null;
    }

    // RestTemplate restTemplate = new RestTemplate();
    // restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    // if (isValidToken(token)) {
    // HttpHeaders headers = new HttpHeaders();
    // headers.add(Constants.AUTH_TOKEN_HEADER, token);
    // headers.add(Constants.SUBJECT_TOKEN_HEADER, token);
    // HttpEntity<Request> requestEntity = new HttpEntity<>(headers);
    // try {
    // HttpEntity<Response> responseEntity =
    // restTemplate.exchange(tokenRequestUrl, HttpMethod.GET, requestEntity, Response.class);
    // return convert(responseEntity);
    // } catch (RestClientException e) {
    // LOGGER.error("Error al buscar la inforación del token, causa: ", e);
    // throw new RestClientException("Error al buscar la inforación del token, causa: ", e);
    // }
    // } else {
    // return null;
    // }
  }

  @Override
  public TokenInfo refreshToken(String token) throws InvalidTokenException {

    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    Identity identity = new Identity();
    Token_ tokenRequest = new Token_();
    Auth auth = new Auth();
    List<String> methods = new ArrayList<>();
    methods.add("token");
    tokenRequest.setId(token);
    identity.setToken(tokenRequest);
    identity.setMethods(methods);
    auth.setIdentity(identity);
    Request request = new Request();
    request.setAuth(auth);
    try {
      HttpEntity<Request> requestEntity = new HttpEntity<>(request);
      HttpEntity<Response> responseEntity =
          restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, requestEntity, Response.class);
      IdentityUser user = convert(responseEntity);
      return user.getTokenInfo();
    } catch (RestClientException e) {
      LOGGER.error("Error al hacer la peticion a keystone, casua: ", e);
      throw new InvalidTokenException("Error al generar la petición de para nuevo token: ", e);
    }


  }

  @Override
  public boolean isValidToken(String token) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

    try {
      String adminToken = adminUtils.getAdmintoken();
      HttpHeaders headers = new HttpHeaders();
      headers.add(Constants.AUTH_TOKEN_HEADER, adminToken);
      headers.add(Constants.SUBJECT_TOKEN_HEADER, token);
      HttpEntity<Request> requestEntity = new HttpEntity<>(headers);
      HttpEntity<Response> responseEntity =
          restTemplate.exchange(tokenRequestUrl, HttpMethod.GET, requestEntity, Response.class);
      IdentityUser user = convert(responseEntity);
      if (user.getTokenInfo() != null && !user.getTokenInfo().getToken().isEmpty()) {
        invalidToken(adminToken);
        return true;
      } else {
        return false;
      }
    } catch (RestClientException | ServiceException e) {
      LOGGER.error("Error al validar el token, causa: ", e);
    }
    return false;

  }

  @Override
  public boolean invalidToken(String token) {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    HttpHeaders headers = new HttpHeaders();
    headers.add(Constants.AUTH_TOKEN_HEADER, token);
    headers.add(Constants.SUBJECT_TOKEN_HEADER, token);
    HttpEntity<String> requestEntity = new HttpEntity<>(headers);
    try {
      HttpEntity<String> responseEntity =
          restTemplate.exchange(tokenRequestUrl, HttpMethod.DELETE, requestEntity, String.class);
      if (responseEntity.toString().contains(HttpStatus.NO_CONTENT.toString())) {
        return true;
      }

      return false;
    } catch (RestClientException e) {
      return false;
    }

  }

  private IdentityUser convert(HttpEntity<Response> responseEntity) {
    Response response = responseEntity.getBody();
    HttpHeaders headers = responseEntity.getHeaders();

    if (response.getToken() == null && response.getToken().getUser() == null) {
      return null;
    } else {
      IdentityUser idmUser = new IdentityUser();

      if (response.getToken().getRoles() != null
          && !response.getToken().getUser().getName().equals(adminUser)) {
        Set<Role> roles = new HashSet<>();

        response.getToken().getRoles().forEach((role) -> {
          Role roleKey = RoleUtil.getInstance().validateRole(role.getName());
          if (roleKey != null) {
            roles.add(roleKey);
          }
        });

        idmUser.setRoles(roles);
      }

      TokenInfo token = new TokenInfo();

      token.setTokenType(TokenType.OTHER);
      token.setStart(response.getToken().getIssuedAt());
      token.setEnd(response.getToken().getExpiresAt());

      if (token.getStart() != null && token.getEnd() != null) {
        long tmp = token.getEnd().getTime() - token.getStart().getTime();
        token.setTime((int) tmp / 1000);
      }
      idmUser.setTokenInfo(token);

      List<String> tmp = headers.get(Constants.SUBJECT_TOKEN_HEADER);

      if (!tmp.isEmpty()) {
        token.setToken(tmp.get(0));
      }
      // LOGGER.error("" + response.getToken().getUser().getName() + " idmUSer " + adminUser);
      if (idmUser.getRoles() == null
          && !response.getToken().getUser().getName().equals(adminUser)) {
        String adminToken;
        try {
          adminToken = this.adminUtils.getAdmintoken();
          idmUser.setRoles(this.userService
              .getUserFromTokenToIdentityUser(adminToken, token.getToken()).getRoles());
          this.invalidToken(adminToken);
        } catch (ServiceException e) {
          LOGGER.debug("error creando el adminToken en KeystoneLoginService");
        }
      }

      idmUser.setUsername(response.getToken().getUser().getName());
      idmUser.setIdmId(response.getToken().getUser().getId());

      return idmUser;
    }
  }

}
