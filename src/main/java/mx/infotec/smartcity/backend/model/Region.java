package mx.infotec.smartcity.backend.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author Erik Valdivieso
 */
public class Region implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer id;
    @Indexed
    private Integer countryId;
    private String name;
    @Indexed
    private String regionCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
    
}
