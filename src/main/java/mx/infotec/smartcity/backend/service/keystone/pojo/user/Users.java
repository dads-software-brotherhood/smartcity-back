
package mx.infotec.smartcity.backend.service.keystone.pojo.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Users {

    private List<User> users = null;
    // private Links_ links;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    /*
     * public Links_ getLinks() { return links; }
     * 
     * public void setLinks(Links_ links) { this.links = links; }
     */
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
