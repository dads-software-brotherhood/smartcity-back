package mx.infotec.smartcity.backend.service.keystone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.AuthTokenInfo;

@Service
@Qualifier("adminUtils")
public class KeystoneAdminUtilsServiceImpl implements AdminUtilsService {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(KeystoneAdminUtilsServiceImpl.class);

    @Value("${idm.admin.username}")
    private String idmUser;
    @Value("${idm.admin.password}")
    private char[] idmPassword;

    @Autowired
    @Qualifier("keystoneLoginService")
    private KeystoneLoginService loginService;

    @Override
    public String getAdmintoken() throws ServiceException {
        try {
            AuthTokenInfo authTokenInfo = loginService.performAuthToken(idmUser, idmPassword);
            return authTokenInfo.getAuthToken();
        } catch (InvalidCredentialsException e) {
            LOGGER.error("No se pudo validar el usuario admin, causa: ", e);
        }
        return null;
    }

    @Override
    public boolean isAdmin(String token) throws ServiceException {
        try {
            IdentityUser userInfo = loginService.findUserByValidToken(token);
            for (Role role : userInfo.getRoles()) {

                if (role.equals(Role.ADMIN)) {
                    return true;
                }
            }
        } catch (InvalidTokenException e) {
            LOGGER.error("Error to validate if token roles equals to admin");
            throw new ServiceException(e);
        }

        return false;
    }

}
