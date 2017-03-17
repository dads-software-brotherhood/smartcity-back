package mx.infotec.smartcity.backend.service.keystone;

import mx.infotec.smartcity.backend.service.keystone.pojo.Response;
import mx.infotec.smartcity.backend.service.keystone.pojo.User;
import mx.infotec.smartcity.backend.service.keystone.pojo.Request;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.TokenInfo;
import mx.infotec.smartcity.backend.model.TokenType;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;

import org.apache.http.Header;
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

/**
 *
 * @author Erik Valdivieso
 */
@Service("keystoneLoginService")
public class KeystoneLoginServiceImpl implements LoginService {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(KeystoneLoginServiceImpl.class);
    
    @Autowired
    @Qualifier("adminUtils")
    private AdminUtilsService adminUtils;

    @Value("${idm.servers.keystone}")
    private String keystonUrl;

    private String tokenRequestUrl;

    @PostConstruct
    protected void init() {
        tokenRequestUrl = keystonUrl + "/v3/auth/tokens";

        LOGGER.info("Token url: {}", tokenRequestUrl);
    }

    @Override
    public IdentityUser performLogin(String username, char[] password) throws InvalidCredentialsException {
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            Request request = new Request(new User(username, new String(password)));
            HttpEntity<Request> requestEntity = new HttpEntity(request);
            HttpEntity<Response> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, requestEntity, Response.class);

            return convert(responseEntity);
        } catch (Exception ex) {
            throw new InvalidCredentialsException(ex);
        }
    }

    @Override
    public TokenInfo refreshToken(String token) throws InvalidTokenException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValidToken(String token) {
      String adminToken = adminUtils.getAdmintoken();
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
      HttpHeaders headers = new HttpHeaders();
      headers.add("X-Auth-Token", adminToken);
      headers.add("X-Subject-Token", token);
      HttpEntity<Request> requestEntity = new HttpEntity<>(headers);
      try {
        HttpEntity<Response> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.GET, requestEntity, Response.class);
        IdentityUser user = convert(responseEntity);
        if (user.getTokenInfo() != null && !user.getTokenInfo().getToken().isEmpty()) {
          invalidToken(adminToken);
          return true;
        } else {
          return false;
        }   
      } catch(RestClientException e) {
        LOGGER.error("Error al validar el token, causa: ", e);
      }
      return false;
        
    }

    @Override
    public boolean invalidToken(String token) {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
      HttpHeaders headers = new HttpHeaders();
      headers.add("X-Auth-Token", token);
      headers.add("X-Subject-Token", token);
      HttpEntity<String> requestEntity = new HttpEntity<>(headers);
      try {
          HttpEntity<String> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.DELETE, requestEntity, String.class);
          if(responseEntity.toString().contains(HttpStatus.NO_CONTENT.toString())) {
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

            if (response.getToken().getRoles() != null) {
                Set<String> roles = new HashSet<>();

                response.getToken().getRoles().forEach((role) -> {
                    roles.add(role.getName());
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

            List<String> tmp = headers.get("x-subject-token");

            if (!tmp.isEmpty()) {
                token.setToken(tmp.get(0));
            }

            idmUser.setTokenInfo(token);

            idmUser.setUsername(response.getToken().getUser().getName());

            return idmUser;
        }
    }

}
