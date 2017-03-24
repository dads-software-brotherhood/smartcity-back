
package mx.infotec.smartcity.backend.service.keystone.pojo.token;

import java.io.Serializable;

public class User implements Serializable
{

    private Domain_ domain;
    private String id;
    private String name;
    private final static long serialVersionUID = -8744943037435246148L;

    public Domain_ getDomain() {
        return domain;
    }

    public void setDomain(Domain_ domain) {
        this.domain = domain;
    }

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
