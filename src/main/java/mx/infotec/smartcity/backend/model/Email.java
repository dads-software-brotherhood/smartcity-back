package mx.infotec.smartcity.backend.model;

import java.io.Serializable;
import java.util.Map;

public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
  
  private String to;
  private String from;
  private String message;
  private Map<String, Object> content;
  
  public String getTo() {
    return to;
  }
  public void setTo(String to) {
    this.to = to;
  }
  public String getFrom() {
    return from;
  }
  public void setFrom(String from) {
    this.from = from;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }
}
