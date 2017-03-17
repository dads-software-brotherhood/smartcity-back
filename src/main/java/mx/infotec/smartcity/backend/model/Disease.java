package mx.infotec.smartcity.backend.model;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.annotation.Id;

/**
 *
 * @author Erik Valdivieso
 */
public class Disease implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    private String name;
    
    private List<Symptom> symptoms;

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

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }
    
}
