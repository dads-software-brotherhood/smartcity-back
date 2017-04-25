package mx.infotec.smartcity.backend.service.keystone.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author Erik Valdivieso
 */
@JsonInclude(Include.NON_NULL)
public class Auth implements Serializable {

    private static final long serialVersionUID = 1L;

    private Identity identity;
    private Scope scope;

    public Auth() {
    }

    public Auth(User user) {
        this.identity = new Identity(user);
    }

    public Auth(User user, String idDomain) {
        this.identity = new Identity(user);
        this.scope = new Scope(idDomain);
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
