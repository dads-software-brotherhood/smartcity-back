package mx.infotec.smartcity.backend.model;

import java.io.Serializable;

public class UserModel implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String name;
  
  private String familyName;
  
  private String email;
  
  private Role role;
  
  private String message;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
    
}
