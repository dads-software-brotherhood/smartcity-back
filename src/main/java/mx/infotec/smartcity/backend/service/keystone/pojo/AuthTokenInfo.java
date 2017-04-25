package mx.infotec.smartcity.backend.service.keystone.pojo;

import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
public class AuthTokenInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private TokenResponse tokenResponse;
    private String authToken;

    public AuthTokenInfo() {
    }

    public AuthTokenInfo(TokenResponse tokenResponse, String authToken) {
        this.tokenResponse = tokenResponse;
        this.authToken = authToken;
    }    
    
    public TokenResponse getTokenResponse() {
        return tokenResponse;
    }

    public void setTokenResponse(TokenResponse tokenResponse) {
        this.tokenResponse = tokenResponse;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
