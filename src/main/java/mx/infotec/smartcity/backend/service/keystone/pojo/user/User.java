
package mx.infotec.smartcity.backend.service.keystone.pojo.user;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = 8643545259845623320L;
    private Object username;
    private String name;
    private Links links;
    private String passwordChangedAt;
    private Boolean enabled;
    private String domainId;
    private String defaultProjectId;
    private String id;
    private String password;
    private String password_expires_at;

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*
     * public Links getLinks() { return links; }
     * 
     * public void setLinks(Links links) { this.links = links; }
     */
    public String getPasswordChangedAt() {
        return passwordChangedAt;
    }

    public void setPasswordChangedAt(String passwordChangedAt) {
        this.passwordChangedAt = passwordChangedAt;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    public String getDefaultProjectId() {
        return defaultProjectId;
    }

    public void setDefaultProjectId(String defaultProjectId) {
        this.defaultProjectId = defaultProjectId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getPassword_expires_at() {
        return password_expires_at;
    }

    public void setPassword_expires_at(String password_expires_at) {
        this.password_expires_at = password_expires_at;
    }

}
