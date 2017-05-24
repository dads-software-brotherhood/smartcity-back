package mx.infotec.smartcity.backend.model.transport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"id", "name", "description", "brandName", "modelName", "passengersTotal", "fuelType",
    "fuelConsumption", "height", "width", "depth", "weight", "dateModified", "dateCreated", "transportSchedules", "creatorId"})
@Document
public class PublicTransport {

    @Id
    private String id;
    private String name;
    private String description;
    private String brandName;
    private String modelName;
    private Integer passengersTotal;
    @DBRef
    @JsonProperty("fuelType")
    private PublicTransportFuelType publicTransportFuelType;
    private String fuelConsumption;
    private Integer height;
    private Integer width;
    private Integer depth;
    private Integer weight;
    private Date dateModified;
    private Date dateCreated;
    @DBRef
    List<TransportSchedule> transportSchedules;

    private String creatorId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getPassengersTotal() {
        return passengersTotal;
    }

    public void setPassengersTotal(Integer passengersTotal) {
        this.passengersTotal = passengersTotal;
    }

    public PublicTransportFuelType getPublicTransportFuelType() {
        return publicTransportFuelType;
    }

    public void setPublicTransportFuelType(PublicTransportFuelType publicTransportFuelType) {
        this.publicTransportFuelType = publicTransportFuelType;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<TransportSchedule> getTransportSchedules() {
        return transportSchedules;
    }

    public void setTransportSchedules(List<TransportSchedule> transportSchedules) {
        this.transportSchedules = transportSchedules;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

}
