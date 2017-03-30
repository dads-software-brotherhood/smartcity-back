package mx.infotec.smartcity.backend.utils;

/**
 *
 * @author Erik Valdivieso
 */
public interface Constants {
    
    String AUTH_TOKEN_HEADER = "X-Auth-Token";
    String SUBJECT_TOKEN_HEADER = "X-Subject-Token";
    
    String USER_REQUES_KEY = "identityUser";
    
    String RECOVERY_TOKEN = "recovery-token";
    
    public static final String ENCODING = "UTF-8";
    public static final String FORTMAT_TEXT_HTML = "html";
    
    public static final String ADMIN_ROLE = "admin";
    
    public static final String RECOVERY_PASSWORD_URL = "/restore-password/";
    public static final String VALIDATE_ACCOUNT_URL = "/account-verification/";
    
}
