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
import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.Auth;
import mx.infotec.smartcity.backend.service.keystone.pojo.AuthTokenInfo;
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
public class KeystoneLoginServiceImpl implements KeystoneLoginService {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(KeystoneLoginServiceImpl.class);

    @Autowired
    @Qualifier("adminUtils")
    private AdminUtilsService adminUtils;

    @Autowired
    @Qualifier("keystoneUserService")
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Value("${idm.servers.keystone}")
    private String keystonUrl;

    @Value("${idm.default.domain}")
    private String defaultDomain;

    private String tokenRequestUrl;

    // TODO remove this once the sistem has his own admin and not using anymore
    // idm
    @Value("${idm.admin.username}")
    private String idmUser;

    @PostConstruct
    protected void init() {
        tokenRequestUrl = keystonUrl + "/v3/auth/tokens";

        LOGGER.debug("Token url: {}", tokenRequestUrl);
    }

    @Override
    public IdentityUser performLogin(String username, char[] password) throws InvalidCredentialsException {
        try {
            HttpEntity<Response> responseEntity = performRestLogin(username, password, false);
            return convert(responseEntity);
        } catch (Exception ex) {
            throw new InvalidCredentialsException(ex);
        }
    }

    @Override
    public AuthTokenInfo performAuthToken(String username, char[] password) throws InvalidCredentialsException {
        try {
            HttpEntity<Response> responseEntity = performRestLogin(username, password, true);

            AuthTokenInfo authTokenInfo = new AuthTokenInfo();
            authTokenInfo.setTokenResponse(responseEntity.getBody().getToken());

            HttpHeaders headers = responseEntity.getHeaders();

            List<String> tmp = headers.get(Constants.SUBJECT_TOKEN_HEADER);

            if (!tmp.isEmpty()) {
                authTokenInfo.setAuthToken(tmp.get(0));
            }

            return authTokenInfo;
        } catch (Exception ex) {
            throw new InvalidCredentialsException(ex);
        }
    }

    private HttpEntity<Response> performRestLogin(String username, char[] password, boolean isAdmin) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        Request request;
        // not logging in with scope for idm user because else getting error
        // not authorized
        // TODO remove username.equals(idmUser) should not be used in the future
        if (isAdmin || username.equals(idmUser)) {
            request = new Request(new User(username, new String(password)));
        } else {
            request = new Request(new User(username, new String(password)), this.defaultDomain);
        }
        HttpEntity<Request> requestEntity = new HttpEntity<>(request);
        HttpEntity<Response> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, requestEntity,
                Response.class);

        return responseEntity;

    }

    @Deprecated
    private HttpEntity<Response> performRestLogin(String username, char[] password) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        Request request = new Request(new User(username, new String(password)), this.defaultDomain);
        HttpEntity<Request> requestEntity = new HttpEntity<>(request);
        HttpEntity<Response> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, requestEntity,
                Response.class);

        return responseEntity;
    }

    @Override
    public IdentityUser findUserByValidToken(String token) throws InvalidTokenException, ServiceException {
        if (token != null) {
            String tokenAdmin = adminUtils.getAdmintoken();
            IdentityUser idu = null;

            try {
                idu = userService.getUserFromTokenToIdentityUser(tokenAdmin, token);
            } finally {
                this.invalidToken(tokenAdmin);
            }

            return idu;
        } else {
            return null;
        }
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
            HttpEntity<Response> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.POST, requestEntity,
                    Response.class);
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
            HttpEntity<Response> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.GET, requestEntity,
                    Response.class);
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
            HttpEntity<String> responseEntity = restTemplate.exchange(tokenRequestUrl, HttpMethod.DELETE, requestEntity,
                    String.class);
            return responseEntity.toString().contains(HttpStatus.NO_CONTENT.toString());
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
            IdentityUser identityUser = new IdentityUser();

            if (response.getToken().getRoles() != null) {
                Set<Role> roles = new HashSet<>();
                
                response.getToken().getRoles().forEach((role) -> {
                    Role roleKey = RoleUtil.validateRole(role.getName());
                    if (roleKey != null) {
                        roles.add(roleKey);
                    }
                });

                identityUser.setRoles(roles);
            }

            TokenInfo token = new TokenInfo();

            token.setTokenType(TokenType.OTHER);
            token.setStart(response.getToken().getIssuedAt());
            token.setEnd(response.getToken().getExpiresAt());

            if (token.getStart() != null && token.getEnd() != null) {
                long tmp = token.getEnd().getTime() - token.getStart().getTime();
                token.setTime((int) tmp / 1000);
            }
            identityUser.setTokenInfo(token);

            List<String> tmp = headers.get(Constants.SUBJECT_TOKEN_HEADER);

            if (!tmp.isEmpty()) {
                token.setToken(tmp.get(0));
            }

            identityUser.setUsername(response.getToken().getUser().getName());
            identityUser.setIdmId(response.getToken().getUser().getId());

            return identityUser;
        }
    }

}
