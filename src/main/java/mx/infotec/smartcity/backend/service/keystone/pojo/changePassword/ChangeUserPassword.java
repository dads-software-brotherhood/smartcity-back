
package mx.infotec.smartcity.backend.service.keystone.pojo.changePassword;

import java.io.Serializable;

public class ChangeUserPassword implements Serializable
{

    private User_ user;
    private final static long serialVersionUID = 7591862309001175712L;

    public User_ getUser() {
        return user;
    }

    public void setUser(User_ user) {
        this.user = user;
    }

}
