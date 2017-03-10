package mx.infotec.smartcity.backend.service.horizon.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import mx.infotec.smartcity.backend.service.keystone.pojo.Role;

/**
 *
 * @author Erik Valdivieso
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String displayName;
    private String email;
    private List<Role> roles;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
}
