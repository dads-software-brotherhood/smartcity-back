
package mx.infotec.smartcity.backend.model.transport;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "coordinates"
})
@Deprecated
public class Stops {

    @JsonProperty("type")
    private String type;
    @JsonProperty("coordinates")
    private List<List<Double>> coordinates = null;

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("coordinates")
    public List<List<Double>> getCoordinates() {
        return coordinates;
    }

    @JsonProperty("coordinates")
    public void setCoordinates(List<List<Double>> coordinates) {
        this.coordinates = coordinates;
    }

}
