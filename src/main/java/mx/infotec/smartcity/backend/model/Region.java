package mx.infotec.smartcity.backend.model;

import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 *
 * @author Erik Valdivieso
 */
public class Region implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer id;
    private String name;
    @Indexed
    private String regionCode;
    
    @Indexed
    @DBRef
    private Country country;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
}
