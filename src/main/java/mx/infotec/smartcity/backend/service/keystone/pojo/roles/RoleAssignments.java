package mx.infotec.smartcity.backend.service.keystone.pojo.roles;

import java.io.Serializable;

import mx.infotec.smartcity.backend.service.keystone.pojo.user.User;

public class RoleAssignments implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }    
}
