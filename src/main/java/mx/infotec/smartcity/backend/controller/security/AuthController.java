package mx.infotec.smartcity.backend.controller.security;

import java.util.Date;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.TokenInfo;
import mx.infotec.smartcity.backend.model.TokenRequest;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;
import mx.infotec.smartcity.backend.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private UserProfileRepository profileRepository;

    @Value("${idm.admin.username}")
    private String idmUser;
    
    /**
     * Method used to perform the user authentication.
     *
     * If the credentials are invalid, the event must be saved in the log.
     *
     * @param tokenRequest User information.
     * @return If the user is valid, IdentityUser object is returned. Otherwise
     * an error code (HttpStatus.UNAUTHORIZED).
     * @see IdentityUser, HttpStatus
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> login(@RequestBody TokenRequest tokenRequest) {
        if (tokenRequest.getUsername() != null && tokenRequest.getPassword() != null && !tokenRequest.getUsername().equalsIgnoreCase(idmUser)) {
            try {
                IdentityUser identityUser = loginService.performLogin(tokenRequest.getUsername(), tokenRequest.getPassword());

                UserProfile userProfile = profileRepository.findByKeystoneId(identityUser.getIdmId());

                //TODO: Pasar al servicio del user profile
                if (userProfile == null) {
                    LOGGER.warn("Local user don't  found. It will be create a new one");

                    userProfile = new UserProfile();
                    userProfile.setKeystoneId(identityUser.getIdmId());
                    userProfile.setEmail(tokenRequest.getUsername());
                    userProfile.setName(tokenRequest.getUsername());
                    userProfile.setRegisterDate(new Date());

                    profileRepository.insert(userProfile);
                }

                StringBuilder sb = new StringBuilder();
                sb.append(userProfile.getName());

                if (userProfile.getFamilyName() != null) {
                    sb.append(' ').append(userProfile.getFamilyName());
                }

                identityUser.setMongoId(userProfile.getId());
                identityUser.setName(sb.toString());

                return ResponseEntity.accepted().body(identityUser);
            } catch (InvalidCredentialsException ex) {
                //TODO: Debe agregar el error a la bitacora (es un acceso erroneo)

                LOGGER.error("Error at perform authentication", ex);
            }
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password invalid");
    }

    /**
     * Method used to logout the user (invalidates the user's token).
     *
     * @param tokenAuth User's token
     * @return Accept response.
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = Constants.AUTH_TOKEN_HEADER) String tokenAuth) {
        if (!loginService.invalidToken(tokenAuth)) {
            LOGGER.warn("Invalid token: {}", tokenAuth);
        } else {
            LOGGER.info("Deleted token: {}", tokenAuth);
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("loggout");
    }

    /**
     * Method used to verify the validity of a token.
     *
     * @param tokenAuth User's token
     * @return If the token is valid, an accept code (HttpStatus.ACCEPTED); In
     * another case an error code (HttpStatus.UNAUTHORIZED)
     * @see HttpStatus
     */
    @RequestMapping(method = RequestMethod.GET, value = "/valid-token")
    public ResponseEntity<?> validToken(@RequestHeader(name = Constants.AUTH_TOKEN_HEADER) String tokenAuth) {

        if (loginService.isValidToken(tokenAuth)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("success");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error or invalid token");
        }

    }

    /**
     * Method used to obtain a new token from the user's current token.
     *
     * If the token is invalid, the event must be saved in the log
     *
     * @param tokenAuth User's token
     * @return In case the token is valid and a new token can be generated, a
     * Token object. Otherwise an error code (HttpStatus.UNAUTHORIZED)
     * @see TokenInfo HttpStatus
     */
    @RequestMapping(method = RequestMethod.POST, value = "/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader(name = Constants.AUTH_TOKEN_HEADER) String tokenAuth) {
        //TODO: Debe regrescar el token por medio del servicio y devolverlo, si no es válido debe agregarlo a la bitácora

        TokenInfo token;
        try {
            token = loginService.refreshToken(tokenAuth);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
        } catch (InvalidTokenException e) {
            LOGGER.error("Error at refresh token", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error or invalid token");
        }
    }

}
