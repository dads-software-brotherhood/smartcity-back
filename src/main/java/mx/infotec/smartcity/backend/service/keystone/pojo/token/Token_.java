
package mx.infotec.smartcity.backend.service.keystone.pojo.token;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public class Token_ implements Serializable {

  private List<String>      methods          = null;
  private List<Role>        roles            = null;
  private String            expiresAt;
  private Project           project;
  private Extras            extras;
  private User              user;

  @JsonProperty("audit_id")
  private List<String>      auditIds         = null;
  @JsonProperty("issued_at")
  private String            issuedAt;
  private final static long serialVersionUID = 5568623456871918791L;

  public List<String> getMethods() {
    return methods;
  }

  public void setMethods(List<String> methods) {
    this.methods = methods;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public String getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(String expiresAt) {
    this.expiresAt = expiresAt;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Extras getExtras() {
    return extras;
  }

  public void setExtras(Extras extras) {
    this.extras = extras;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @JsonProperty("audit_id")
  public List<String> getAuditIds() {
    return auditIds;
  }

  @JsonProperty("audit_id")
  public void setAuditIds(List<String> auditIds) {
    this.auditIds = auditIds;
  }

  @JsonProperty("issued_at")
  public String getIssuedAt() {
    return issuedAt;
  }

  @JsonProperty("issued_at")
  public void setIssuedAt(String issuedAt) {
    this.issuedAt = issuedAt;
  }

}
