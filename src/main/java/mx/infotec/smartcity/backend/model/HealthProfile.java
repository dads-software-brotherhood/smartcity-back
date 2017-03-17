package mx.infotec.smartcity.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author Erik Valdivieso
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String healthState;
    @Indexed
    private String idDisease;
    private String description;
    private String symptoms;
    private String treatment;

    public String getHealthState() {
        return healthState;
    }

    public void setHealthState(String healthState) {
        this.healthState = healthState;
    }

    public String getIdDisease() {
        return idDisease;
    }

    public void setIdDisease(String idDisease) {
        this.idDisease = idDisease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
}
