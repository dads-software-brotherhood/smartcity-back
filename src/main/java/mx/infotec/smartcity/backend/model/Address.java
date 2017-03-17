package mx.infotec.smartcity.backend.model;

import java.io.Serializable;

/**
 *
 * @author Erik Valdivieso
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String addreddCountry;
    private String addreddRegion;
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
