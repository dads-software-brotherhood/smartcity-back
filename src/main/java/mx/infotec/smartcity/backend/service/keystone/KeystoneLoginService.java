package mx.infotec.smartcity.backend.service.keystone;

import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import mx.infotec.smartcity.backend.service.keystone.pojo.AuthTokenInfo;

/**
 *
 * @author Erik Valdivieso
 */
public interface KeystoneLoginService extends LoginService {

    AuthTokenInfo performAuthToken(String username, char[] password) throws InvalidCredentialsException;

}
