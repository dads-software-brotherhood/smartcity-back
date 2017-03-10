package mx.infotec.smartcity.backend.service.keystone.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Erik Valdivieso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> methods;
    private List<Role> roles;
    private Date expiresAt;
    private Date issuedAt;
    private User user;

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @JsonProperty("expires_at")
    public Date getExpiresAt() {
        return expiresAt;
    }

    @JsonProperty("expires_at")
    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    @JsonProperty("issued_at")
    public Date getIssuedAt() {
        return issuedAt;
    }

    @JsonProperty("issued_at")
    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
