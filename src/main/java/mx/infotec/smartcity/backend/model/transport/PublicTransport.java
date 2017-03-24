
package mx.infotec.smartcity.backend.model.transport;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "type", "name", "vehicleType", "category", "stops", "areaServed",
    "arrivalTime", "returnTime", "vehiclePlateIdentifier", "dateCreated", "dateModified"})
public class PublicTransport {
  @Id
  @JsonProperty("id")
  private String       id;
  @JsonProperty("type")
  private String       type;
  @JsonProperty("name")
  private String       name;
  @JsonProperty("vehicleType")
  private String       vehicleType;
  @JsonProperty("category")
  private List<String> category = null;
  @JsonProperty("stops")
  private Stops        stops;
  @JsonProperty("areaServed")
  private String       areaServed;
  @JsonProperty("arrivalTime")
  private String       arrivalTime;
  @JsonProperty("returnTime")
  private String       returnTime;
  @JsonProperty("vehiclePlateIdentifier")
  private String       vehiclePlateIdentifier;
  @JsonProperty("dateCreated")
  private String       dateCreated;
  @JsonProperty("dateModified")
  private String       dateModified;

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
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

  @JsonProperty("name")
  public String getName() {
    return name;
  }

  @JsonProperty("name")
  public void setName(String name) {
    this.name = name;
  }

  @JsonProperty("vehicleType")
  public String getVehicleType() {
    return vehicleType;
  }

  @JsonProperty("vehicleType")
  public void setVehicleType(String vehicleType) {
    this.vehicleType = vehicleType;
  }

  @JsonProperty("category")
  public List<String> getCategory() {
    return category;
  }

  @JsonProperty("category")
  public void setCategory(List<String> category) {
    this.category = category;
  }

  @JsonProperty("stops")
  public Stops getStops() {
    return stops;
  }

  @JsonProperty("stops")
  public void setStops(Stops stops) {
    this.stops = stops;
  }

  @JsonProperty("areaServed")
  public String getAreaServed() {
    return areaServed;
  }

  @JsonProperty("areaServed")
  public void setAreaServed(String areaServed) {
    this.areaServed = areaServed;
  }

  @JsonProperty("arrivalTime")
  public String getArrivalTime() {
    return arrivalTime;
  }

  @JsonProperty("arrivalTime")
  public void setArrivalTime(String arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  @JsonProperty("returnTime")
  public String getReturnTime() {
    return returnTime;
  }

  @JsonProperty("returnTime")
  public void setReturnTime(String returnTime) {
    this.returnTime = returnTime;
  }

  @JsonProperty("vehiclePlateIdentifier")
  public String getVehiclePlateIdentifier() {
    return vehiclePlateIdentifier;
  }

  @JsonProperty("vehiclePlateIdentifier")
  public void setVehiclePlateIdentifier(String vehiclePlateIdentifier) {
    this.vehiclePlateIdentifier = vehiclePlateIdentifier;
  }

  @JsonProperty("dateCreated")
  public String getDateCreated() {
    return dateCreated;
  }

  @JsonProperty("dateCreated")
  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  @JsonProperty("dateModified")
  public String getDateModified() {
    return dateModified;
  }

  @JsonProperty("dateModified")
  public void setDateModified(String dateModified) {
    this.dateModified = dateModified;
  }

}
