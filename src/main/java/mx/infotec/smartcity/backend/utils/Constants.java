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
    
    public static final String URL_ADMIN = "admin";
    public static final String URL_GROUPS = "groups";
    public static final String URL_PROFILE = "user-profile";
    public static final String URL_TRANSPORT = "public-transports";
    public static final String URL_RULES = "rules";
   
    public static final String GENERAL_PARAM_NAME = "name";
    public static final String GENERAL_PARAM_ID = "id";
    
    
}
