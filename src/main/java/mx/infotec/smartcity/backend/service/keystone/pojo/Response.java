package mx.infotec.smartcity.backend.service.keystone.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    private TokenResponse token;

    public TokenResponse getToken() {
        return token;
    }

    public void setToken(TokenResponse token) {
        this.token = token;
    }
}
