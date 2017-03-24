
package mx.infotec.smartcity.backend.service.keystone.pojo.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Links {

  private String self;
  // private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }



}
