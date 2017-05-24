package mx.infotec.smartcity.backend.model.transport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import mx.infotec.smartcity.backend.model.DayName;
import mx.infotec.smartcity.backend.model.Time;

/**
 *
 * @author Infotec
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeekDay implements Serializable {

    private static final long serialVersionUID = 1L;

    private DayName dayName;
    private Time departureTime;
    private Time arrivalTime;
    private boolean active;

    public DayName getDayName() {
        return dayName;
    }

    public void setDayName(DayName dayName) {
        this.dayName = dayName;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
