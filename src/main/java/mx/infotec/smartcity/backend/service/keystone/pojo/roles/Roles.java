
package mx.infotec.smartcity.backend.service.keystone.pojo.roles;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Roles implements Serializable {

    private Links links;
    private List<Role> roles;
    private final static long serialVersionUID = 1173144707708238359L;

    @JsonProperty("role_assignments")
    private List<RoleAssignments> roleAssignments;

    public Roles() {
        roles = null;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<RoleAssignments> getRoleAssignments() {
        return roleAssignments;
    }

    public void setRoleAssignments(List<RoleAssignments> roleAssignments) {
        this.roleAssignments = roleAssignments;
    }

}
