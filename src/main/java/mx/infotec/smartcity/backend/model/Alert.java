package mx.infotec.smartcity.backend.model;


import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;

/**
 *
 * @author Adrian Molina
 */
public class Alert implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    private String type;
    private String alertType;
    private String subtypeAlert;
    private String locationDescription;
    private Date dateTime;
    private String description;
    private String refUser;
    private String refDevice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefUser() {
        return refUser;
    }

    public void setRefUser(String refUser) {
        this.refUser = refUser;
    }

    public String getRefDevice() {
        return refDevice;
    }

    public void setRefDevice(String refDevice) {
        this.refDevice = refDevice;
    }

    public String getSubtypeAlert() {
        return subtypeAlert;
    }

    public void setSubtypeAlert(String subtypeAlert) {
        this.subtypeAlert = subtypeAlert;
    }

    
    
    
    

}
