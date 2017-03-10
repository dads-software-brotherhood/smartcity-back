package mx.infotec.smartcity.backend.service.keystone.pojo;

import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
public class Password implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    public Password() {
    }

    public Password(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
