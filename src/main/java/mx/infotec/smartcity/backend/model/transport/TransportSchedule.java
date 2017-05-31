package mx.infotec.smartcity.backend.model.transport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import mx.infotec.smartcity.backend.model.Time;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Infotec
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document
public class TransportSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    @Indexed(unique = true)
    private String routeName;
    private List<WeekDay> weekDays;
    @Indexed
    private Time frequency;
    @Indexed
    @DBRef
    private Agency agency;
    
    private String creatorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<WeekDay> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(List<WeekDay> weekDays) {
        this.weekDays = weekDays;
    }

    public Time getFrequency() {
        return frequency;
    }

    public void setFrequency(Time frequency) {
        this.frequency = frequency;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
}
