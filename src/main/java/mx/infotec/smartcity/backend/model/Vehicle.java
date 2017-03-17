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
    private String vehicleType;
    private String location;
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

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
    
}
