package mx.infotec.smartcity.backend.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;

/**
 *
 * @author Adrian Molina
 */
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    private String name;

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

}
