package mx.infotec.smartcity.backend.model;

import java.util.Set;

/**
 *
 * @author Erik Valdivieso
 */
public class IdentityUser {

  private String    id;
  private String    name;
  private String    username;
  private TokenInfo tokenInfo;
  private Set<Role> roles;

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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public TokenInfo getTokenInfo() {
    return tokenInfo;
  }

  public void setTokenInfo(TokenInfo tokenInfo) {
    this.tokenInfo = tokenInfo;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}
