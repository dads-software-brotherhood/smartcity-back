package mx.infotec.smartcity.backend.service.horizon;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Token;
import mx.infotec.smartcity.backend.model.TokenType;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;
import mx.infotec.smartcity.backend.service.horizon.pojo.ResponseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Erik Valdivieso
 */
@Service("horizonLoginService")
public class HorizonLoginServiceImpl implements LoginService {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(HorizonLoginServiceImpl.class);
    
    @Value("${idm.oauth.clientId}")
    private String clientId;
    
    @Value("${idm.oauth.clientSecret}")
    private String clientSecret;

    @Value("${idm.servers.horizon}")
    private String horizonUrl;
    
    private String tokenUrl;
    
    private String authKey;
    
    @PostConstruct
    protected void init() {
        tokenUrl = horizonUrl + "/oauth2/token";
        
        StringBuilder sb = new StringBuilder();
        sb.append(clientId).append(':').append(clientSecret);
        
        String tmp = Base64Utils.encodeToString(sb.toString().getBytes());
        
        sb = new StringBuilder();
        sb.append("Basic ").append(tmp);
        
        authKey = sb.toString();
        
        LOGGER.info("Idm URL: {}", tokenUrl);
        LOGGER.info("code: {}", authKey);
    }
    
    @Override
    public IdentityUser performLogin(String username, char[] password) throws InvalidCredentialsException {
        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", "password");
            map.add("username", username);
            map.add("password", new String(password));
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", authKey);
            
            HttpEntity requestEntity = new HttpEntity(map, headers);
            
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<ResponseToken> responseEntity = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, ResponseToken.class);
            
            return convert(username, responseEntity.getBody());
        } catch (Exception ex) {
            throw new InvalidCredentialsException(ex);
        }
    }    
    
    private IdentityUser convert(String username, ResponseToken responseToken) {
        IdentityUser identityUser = new IdentityUser();
        
        identityUser.setUsername(username);
        
        Token token = new Token();
        
        token.setTokenType(TokenType.OAUTH);
        token.setToken(responseToken.getAccessToken());
        token.setRefreshToken(responseToken.getRefreshToken());
        token.setTime(responseToken.getExpiresIn());
        token.setStart(new Date());
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, responseToken.getExpiresIn());
        
        token.setEnd(cal.getTime());
        
        identityUser.setToken(token);
        
        return identityUser;
    }
    
    @Override
    public Token refreshToken(String token) throws InvalidTokenException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValidToken(String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean invalidToken(String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
