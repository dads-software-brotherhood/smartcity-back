package mx.infotec.smartcity.backend.model;

import java.util.Date;

/**
 *
 * @author Erik Valdivieso
 */
public class Token {

    private String token;
    private Date start;
    private Date end;
    private int time;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
