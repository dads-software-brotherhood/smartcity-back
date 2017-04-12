package mx.infotec.smartcity.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;

/**
 *
 * @author Erik Valdivieso
 */
@JsonIgnoreProperties({"idIdm"})
public class IdentityUser {

    private String mongoId;
    private String idmId;
    private String name;
    private String username;
    private TokenInfo tokenInfo;
    private Set<Role> roles;

    @JsonProperty("id")
    public String getMongoId() {
        return mongoId;
    }

    @JsonProperty("id")
    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public String getIdmId() {
        return idmId;
    }

    public void setIdmId(String idmId) {
        this.idmId = idmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
