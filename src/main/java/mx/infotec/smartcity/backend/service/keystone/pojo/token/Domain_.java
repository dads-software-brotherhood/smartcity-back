
package mx.infotec.smartcity.backend.service.keystone.pojo.token;

import java.io.Serializable;

public class Domain_ implements Serializable
{

    private String id;
    private String name;
    private final static long serialVersionUID = -2390871132676739653L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
