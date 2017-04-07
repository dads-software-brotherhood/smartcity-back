
package mx.infotec.smartcity.backend.service.keystone.pojo.createUser;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Roles;

@JsonInclude(Include.NON_NULL)
public class User_ {

  private Boolean enabled;
  private String  name;
  private Links   links;
  private String  password;
  private String  id;
  private String  username;
  private Roles   role;

  @JsonProperty("password_expires_at")
  private Date    passwordExpiresAt;


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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Links getLinks() {
    return links;
  }

  public void setLinks(Links links) {
    this.links = links;
  }

  @JsonProperty("password_expires_at")
  public Date getPasswordExpiresAt() {
    return passwordExpiresAt;
  }

  @JsonProperty("password_expires_at")
  public void setPasswordExpiresAt(Date passwordExpiresAt) {
    this.passwordExpiresAt = passwordExpiresAt;
  }



}
