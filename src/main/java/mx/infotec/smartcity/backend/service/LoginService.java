package mx.infotec.smartcity.backend.service;

import java.io.Serializable;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Token;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;

/**
 *
 * @author Erik Valdivieso
 */
public interface LoginService extends Serializable {

    /**
     * Performs authentication on the IDM server, returns the user information with a valid token.
     * 
     * @param username Username
     * @param password Password
     * @return User information with a valid token
     * @throws mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException
     */
    IdentityUser performLogin(String username, char[] password) throws InvalidCredentialsException;
    Token refreshToken(String token) throws  InvalidTokenException;
    boolean isValidToken(String token);
    boolean invalidToken(String token);

}
