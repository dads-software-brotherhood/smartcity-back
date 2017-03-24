package mx.infotec.smartcity.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String healthState;
    private String[] allergySymptoms;
    private Integer asthmaLevel;

    public String getHealthState() {
        return healthState;
    }

    public void setHealthState(String healthState) {
        this.healthState = healthState;
    }

    public String[] getAllergySymptoms() {
        return allergySymptoms;
    }

    public void setAllergySymptoms(String[] allergySymptoms) {
        this.allergySymptoms = allergySymptoms;
    }

    public Integer getAsthmaLevel() {
        return asthmaLevel;
    }

    public void setAsthmaLevel(Integer asthmaLevel) {
        this.asthmaLevel = asthmaLevel;
    }
}
