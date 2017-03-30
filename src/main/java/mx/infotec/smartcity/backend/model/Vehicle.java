package mx.infotec.smartcity.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Erik Valdivieso
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Vehicle implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String description;
    private VehicleType vehicleType;
    private String brandName;
    private String modelName;
    private String manufacturerName;
    private Float cargoVolume;
    private Float height;
    private Float width;
    private Float depth;
    private Float weight;
    private Date vehicleModelDate;
    private FuelType fuelType;
    private Float fuelConsumption;
    private String vehiclPlateIdentifier;
    private Date dateModified;
    private Date datecreated = new Date();
    
    
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

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
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

    public Date getVehicleModelDate() {
      return vehicleModelDate;
    }

    public void setVehicleModelDate(Date vehicleModelDate) {
      this.vehicleModelDate = vehicleModelDate;
    }

    public FuelType getFuelType() {
      return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
      this.fuelType = fuelType;
    }

    public String getVehiclPlateIdentifier() {
        return vehiclPlateIdentifier;
    }

    public void setVehiclPlateIdentifier(String vehiclPlateIdentifier) {
        this.vehiclPlateIdentifier = vehiclPlateIdentifier;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public Float getFuelConsumption() {
      return fuelConsumption;
    }

    public void setFuelConsumption(Float fuelConsumption) {
      this.fuelConsumption = fuelConsumption;
    }

    public String getManufacturerName() {
      return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
      this.manufacturerName = manufacturerName;
    }

    public Float getCargoVolume() {
      return cargoVolume;
    }

    public void setCargoVolume(Float cargoVolume) {
      this.cargoVolume = cargoVolume;
    }

    public Float getHeight() {
      return height;
    }

    public void setHeight(Float height) {
      this.height = height;
    }

    public Float getWidth() {
      return width;
    }

    public void setWidth(Float width) {
      this.width = width;
    }

    public Float getDepth() {
      return depth;
    }

    public void setDepth(Float depth) {
      this.depth = depth;
    }

    public Float getWeight() {
      return weight;
    }

    public void setWeight(Float weight) {
      this.weight = weight;
    }
    
}
