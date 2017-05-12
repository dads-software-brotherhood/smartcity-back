
package mx.infotec.smartcity.backend.model;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;
import org.springframework.data.mongodb.core.index.Indexed;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "type", "group", "dateCreated", "dateModified"})
public class Group {

  @JsonProperty("id")
  private Integer id;
  @JsonProperty("type")
  @NotEmpty
  private String type;
  
  @JsonProperty("group")
  @NotEmpty
  @Indexed(unique = true)  
  private String group;
  
  @JsonProperty("dateCreated")
  private Date   dateCreated;
  @JsonProperty("dateModified")
  private Date   dateModified;
  @JsonProperty("notificationIds")
  private List<String> notificationIds;
  

  @JsonProperty("id")
  public Integer getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Integer id) {
    this.id = id;
  }

  @JsonProperty("type")
  public String getType() {
    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {
    this.type = type;
  }

  @JsonProperty("group")
  public String getGroup() {
    return group;
  }

  @JsonProperty("group")
  public void setGroup(String group) {
    this.group = group;
  }

  @JsonProperty("dateCreated")
  public Date getDateCreated() {
    return dateCreated;
  }

  @JsonProperty("dateCreated")
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  @JsonProperty("dateModified")
  public Date getDateModified() {
    return dateModified;
  }

  @JsonProperty("dateModified")
  public void setDateModified(Date dateModified) {
    this.dateModified = dateModified;
  }

    @JsonProperty("notificationIds")
    public List<String> getNotificationIds() {
        return notificationIds;
    }
    @JsonProperty("notificationIds")
    public void setNotificationIds(List<String> notificationIds) {
        this.notificationIds = notificationIds;
    }
  
  

}
