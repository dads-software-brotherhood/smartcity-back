package mx.infotec.smartcity.backend.model.transport;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Erik Valdivieso
 */
@Document
public class TransportSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Integer id;
    private String routeName;
    private List<WeekDay> weekDays;
    private int frequencyHours;
    private int frequencyMins;
    @DBRef
    private Agency agency;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getFrequencyHours() {
        return frequencyHours;
    }

    public void setFrequencyHours(int frequencyHours) {
        this.frequencyHours = frequencyHours;
    }

    public int getFrequencyMins() {
        return frequencyMins;
    }

    public void setFrequencyMins(int frequencyMins) {
        this.frequencyMins = frequencyMins;
    }

    public Agency getAgency() {
        return agency;
    }

    public void setAgency(Agency agency) {
        this.agency = agency;
    }
}
