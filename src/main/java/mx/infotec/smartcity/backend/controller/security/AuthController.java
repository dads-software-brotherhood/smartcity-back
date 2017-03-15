package mx.infotec.smartcity.backend.controller.security;

import java.util.Date;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Token;
import mx.infotec.smartcity.backend.model.TokenRequest;
import mx.infotec.smartcity.backend.model.TokenType;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Service used to perform user authentication operations
 * 
 * @author Infotec
 */
@RestController
@RequestMapping("/security")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    @Qualifier("keystoneLoginService")
    private LoginService loginService;

    /**
     * Method used to perform the user authentication. 
     * 
     * @param tokenRequest User information.
     * @return If the user is valid, IdentityUser object is returned. Otherwise an error code (HttpStatus.UNAUTHORIZED).
     * @see IdentityUser, HttpStatus
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(@RequestBody TokenRequest tokenRequest) {
        try {
            return ResponseEntity.accepted().body(loginService.performLogin(tokenRequest.getUsername(), tokenRequest.getPassword()));
        } catch (InvalidCredentialsException ex) {
            LOGGER.error("Error at perform authentication", ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password invalid");
        }
    }
    
    /**
     * Method used to logout the user (invalidates the user's token).
     * 
     * @param tokenAuth User's token
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/logout")
    public void logout(@RequestHeader(value = "token-auth") String tokenAuth) {
        
    }
    
    /**
     * Method used to verify the validity of a token.
     * 
     * @param tokenAuth User's token
     * @return If the token is valid, an accept code (HttpStatus.ACCEPTED); In another case an error code (HttpStatus.UNAUTHORIZED)
     * @see HttpStatus
     */
    @RequestMapping(method = RequestMethod.POST, value = "/valid-token")
    public ResponseEntity<?> validToken(@RequestHeader(value = "token-auth") String tokenAuth) {
        return ResponseEntity.accepted().build();
    }

    /**
     * Method used to obtain a new token from the user's current token.
     * 
     * @param tokenAuth User's token
     * @return In case the token is valid and a new token can be generated, a Token object. Otherwise an error code (HttpStatus.UNAUTHORIZED)
     * @see Token HttpStatus
     */
    @RequestMapping(method = RequestMethod.POST, value = "/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader(value = "token-auth") String tokenAuth) {
        Token token = new Token();
        token.setToken(tokenAuth);
        token.setTokenType(TokenType.OTHER);
        token.setStart(new Date());
        token.setEnd(new Date(System.currentTimeMillis() * 3600 * 1000 ));
        token.setTime(3600);
        
        return ResponseEntity.accepted().body(token);
    }
    
}
