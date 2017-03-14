
package mx.infotec.smartcity.backend.service.keystone.pojo.roles;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class Role implements Serializable {

  private String            id;
  private Links_            links;
  private String            name;
  @JsonProperty("application_id")
  private String            applicationId;
  private final static long serialVersionUID = -8184331202654156266L;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Links_ getLinks() {
    return links;
  }

  public void setLinks(Links_ links) {
    this.links = links;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("application_id")
  public String getApplicationId() {
    return applicationId;
  }

  @JsonProperty("application_id")
  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }



}
