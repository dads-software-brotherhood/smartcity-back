
package mx.infotec.smartcity.backend.service.keystone.pojo.roles;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Roles implements Serializable {

  private Links             links;
  private List<Role>        roles            = null;
  private final static long serialVersionUID = 1173144707708238359L;

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

}
