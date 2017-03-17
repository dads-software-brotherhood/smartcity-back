package mx.infotec.smartcity.backend.model;

import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
public class HealthProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String healthState;
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
