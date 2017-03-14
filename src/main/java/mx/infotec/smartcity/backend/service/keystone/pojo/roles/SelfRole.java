package mx.infotec.smartcity.backend.service.keystone.pojo.roles;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SelfRole implements Serializable {

  private final static long serialVersionUID = -8584331202654156266L;

  private Role              role;

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

}
