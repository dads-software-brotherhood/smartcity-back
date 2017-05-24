
package mx.infotec.smartcity.backend.model.transport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "idProfile", "idPublicTransport"})
public class ProfilePublicTransport {

  public ProfilePublicTransport() {

  }

  public ProfilePublicTransport(String idProfile, String idPublicTransport) {
    this.idProfile = idProfile;
    this.idPublicTransport = idPublicTransport;
  }

  @JsonProperty("id")
  private String id;
  @JsonProperty("idProfile")
  private String idProfile;

  @Indexed(unique = true)
  @JsonProperty("idPublicTransport")
  private String idPublicTransport;

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  @JsonProperty("idProfile")
  public String getIdProfile() {
    return idProfile;
  }

  @JsonProperty("idProfile")
  public void setIdProfile(String idProfile) {
    this.idProfile = idProfile;
  }

  @JsonProperty("idPublicTransport")
  public String getIdPublicTransport() {
    return idPublicTransport;
  }

  @JsonProperty("idPublicTransport")
  public void setIdPublicTransport(String idPublicTransport) {
    this.idPublicTransport = idPublicTransport;
  }

}
