
package mx.infotec.smartcity.backend.service.keystone.pojo.changePassword;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User_ implements Serializable {

  private String            password;

  @JsonProperty("original_password")
  private String            originalPassword;

  private final static long serialVersionUID = 7619107461084428962L;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @JsonProperty("original_password")
  public String getOriginalPassword() {
    return originalPassword;
  }

  @JsonProperty("original_password")
  public void setOriginalPassword(String originalPassword) {
    this.originalPassword = originalPassword;
  }

}
