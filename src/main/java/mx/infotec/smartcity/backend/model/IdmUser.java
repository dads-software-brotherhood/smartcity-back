package mx.infotec.smartcity.backend.model;

import java.util.Set;

/**
 *
 * @author Erik Valdivieso
 */
public class IdmUser {

    private String username;
    private Token token;
    private Set<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

}
