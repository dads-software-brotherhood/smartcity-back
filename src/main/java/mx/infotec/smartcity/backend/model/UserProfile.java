package mx.infotec.smartcity.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Erik Valdivieso
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String email;
    private String name;
    private String familyName;
    private Date birthDate;
    private Gender gender;
    
    private String group;
    private String idOrion;
    private String registerType;
    private Date registerDate;
    private Date lastEntry;
    private Date lastProfileUpdate;
    
    private HealthProfile healthProfile;
    private List<Address> addresses;
    private List<Vehicle> vehicles;
    @DBRef
    private List<Group> groups;
    
    @Indexed(unique = true)
    private String keystoneId;
    
    /**
     * Default constructor
     */
    public UserProfile() {
      
    }

    /**
     * Constructor with search parameters
     * @param id
     * @param email
     * @param name
     * @param familyName
     * @param keystoneId
     */
    public UserProfile(String id, String email, String name, String familyName, String keystoneId) {
      super();
      this.id = id;
      this.email = email;
      this.name = name;
      this.familyName = familyName;
      this.keystoneId = keystoneId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIdOrion() {
        return idOrion;
    }

    public void setIdOrion(String idOrion) {
        this.idOrion = idOrion;
    }

    public String getRegisterType() {
        return registerType;
    }

    public void setRegisterType(String registerType) {
        this.registerType = registerType;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getLastEntry() {
        return lastEntry;
    }

    public void setLastEntry(Date lastEntry) {
        this.lastEntry = lastEntry;
    }

    public Date getLastProfileUpdate() {
        return lastProfileUpdate;
    }

    public void setLastProfileUpdate(Date lastProfileUpdate) {
        this.lastProfileUpdate = lastProfileUpdate;
    }

    public HealthProfile getHealthProfile() {
        return healthProfile;
    }

    public void setHealthProfile(HealthProfile healthProfiles) {
        this.healthProfile = healthProfiles;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public String getKeystoneId() {
      return keystoneId;
    }

    public void setKeystoneId(String keystoneId) {
      this.keystoneId = keystoneId;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    
}
