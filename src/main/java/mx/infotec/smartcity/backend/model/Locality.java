package mx.infotec.smartcity.backend.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author Erik Valdivieso
 */
public class Locality implements Serializable {

    private static final long serialVersionUID = -1757257094830904508L;
    
    @Id
    private Integer id;
    @Indexed
    private Integer regionId;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
