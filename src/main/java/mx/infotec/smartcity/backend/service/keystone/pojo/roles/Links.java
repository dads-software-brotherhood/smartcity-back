
package mx.infotec.smartcity.backend.service.keystone.pojo.roles;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Links implements Serializable {

  private Object            next;
  private Object            previous;
  private String            self;
  private final static long serialVersionUID = -2639795953878468295L;

  public Object getNext() {
    return next;
  }

  public void setNext(Object next) {
    this.next = next;
  }

  public Object getPrevious() {
    return previous;
  }

  public void setPrevious(Object previous) {
    this.previous = previous;
  }

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

}
