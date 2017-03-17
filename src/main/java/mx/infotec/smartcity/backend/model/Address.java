package mx.infotec.smartcity.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author Erik Valdivieso
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Indexed
    private String addreddCountry;
    @Indexed
    private String addreddRegion;
    @Indexed
    private String addreddLocality;
    private String street;
    private String postalCode;

    public String getAddreddCountry() {
        return addreddCountry;
    }

    public void setAddreddCountry(String addreddCountry) {
        this.addreddCountry = addreddCountry;
    }

    public String getAddreddRegion() {
        return addreddRegion;
    }

    public void setAddreddRegion(String addreddRegion) {
        this.addreddRegion = addreddRegion;
    }

    public String getAddreddLocality() {
        return addreddLocality;
    }

    public void setAddreddLocality(String addreddLocality) {
        this.addreddLocality = addreddLocality;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
}
