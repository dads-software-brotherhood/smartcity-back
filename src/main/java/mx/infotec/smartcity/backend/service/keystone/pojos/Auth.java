package mx.infotec.smartcity.backend.service.keystone.pojos;

import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
public class Auth implements Serializable {

    private static final long serialVersionUID = 1L;

    private Identity identity;

    public Auth() {
    }

    public Auth(User user) {
        this.identity = new Identity(user);
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }
}
