package mx.infotec.smartcity.backend.service;

import java.io.Serializable;
import mx.infotec.smartcity.backend.model.IdmUser;
import mx.infotec.smartcity.backend.model.Token;

/**
 *
 * @author Erik Valdivieso
 */
public interface LoginService extends Serializable {

    IdmUser performLogin(String username, char[] password);
    Token refreshToken(String token);
    boolean validToken(String token);

}
