package mx.infotec.smartcity.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 *
 * @author Erik Valdivieso
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Indexed
    private int countryId;
    @Indexed
    private int regionId;
    @Indexed
    private int localityId;
    
    private String addreddCountry;
    private String addreddRegion;
    private String addreddLocality;
    private String street;
    private String postalCode;
    private AddressType addressType;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getLocalityId() {
        return localityId;
    }

    public void setLocalityId(int localityId) {
        this.localityId = localityId;
    }

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

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }
    
}
