package mx.infotec.smartcity.backend.service.keystone.pojo;

import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    private Auth auth;

    public Request() {
    }

    public Request(User user) {
        this.auth = new Auth(user);
    }

    public Request(User user, String idDomain) {
        this.auth = new Auth(user, idDomain);
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}
