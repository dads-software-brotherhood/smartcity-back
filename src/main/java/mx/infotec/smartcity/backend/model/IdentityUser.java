package mx.infotec.smartcity.backend.model;

import java.util.Set;

/**
 *
 * @author Erik Valdivieso
 */
public class IdentityUser {

    private String username;
    private TokenInfo tokenInfo;
    private Set<String> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

}
