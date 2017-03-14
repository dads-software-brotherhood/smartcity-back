
package mx.infotec.smartcity.backend.service.keystone.pojo.createUser;

public class User_ {

  private Boolean enabled;
  private String  name;
  private String  password;
  private String  id;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }



}
