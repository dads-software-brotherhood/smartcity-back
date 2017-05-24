package mx.infotec.smartcity.backend.utils;

import javax.servlet.http.HttpServletRequest;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Infotec
 */
public final class ControllerUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerUtils.class);

    private ControllerUtils() {
    }

    public static String getUserId(HttpServletRequest request) {
        IdentityUser identityUser = (IdentityUser) request.getAttribute(Constants.USER_REQUES_KEY);
        
        if (identityUser == null) {
            LOGGER.warn("Cant find user logged");
            return null;
        } else {
            return identityUser.getMongoId();
        }
    }
    
    public static boolean canDeleteUpdate(String creatorId, HttpServletRequest request) {
        if (creatorId != null) {
            IdentityUser identityUser = (IdentityUser) request.getAttribute(Constants.USER_REQUES_KEY);

            return identityUser != null && (isSa(identityUser) || identityUser.getMongoId().equals(creatorId));
        }

        return false;
    }

    public static boolean isSa(IdentityUser identityUser) {
        if (identityUser.getRoles() != null && !identityUser.getRoles().isEmpty()) {
            if (identityUser.getRoles().stream().anyMatch((role) -> (role == Role.SA))) {
                return true;
            }
        }

        return false;
    }

}
