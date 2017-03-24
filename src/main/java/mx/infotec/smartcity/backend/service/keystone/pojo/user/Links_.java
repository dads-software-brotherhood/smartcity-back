
package mx.infotec.smartcity.backend.service.keystone.pojo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Links_ {

  private String self;
  private Object previous;
  private Object next;
  // private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

  public Object getPrevious() {
    return previous;
  }

  public void setPrevious(Object previous) {
    this.previous = previous;
  }

  public Object getNext() {
    return next;
  }

  public void setNext(Object next) {
    this.next = next;
  }



}
