package mx.infotec.smartcity.backend.model.transport;

import java.io.Serializable;
import mx.infotec.smartcity.backend.model.DayName;
import org.joda.time.LocalTime;

/**
 *
 * @author Infotec
 */
public class WeekDay implements Serializable {

    private static final long serialVersionUID = 1L;

    private DayName dayName;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private boolean active;

    public DayName getDayName() {
        return dayName;
    }

    public void setDayName(DayName dayName) {
        this.dayName = dayName;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
