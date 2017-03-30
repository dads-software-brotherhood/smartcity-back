package mx.infotec.smartcity.backend.model;

public class RoleId {
  private Role   role;
  private String id;

  public RoleId(Role role, String id) {
    this.role = role;
    this.id = id;
  }

  public RoleId() {

  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }



}
