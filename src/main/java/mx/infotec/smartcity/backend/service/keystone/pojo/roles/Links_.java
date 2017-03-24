
package mx.infotec.smartcity.backend.service.keystone.pojo.roles;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Links_ implements Serializable {

  private String            self;
  private final static long serialVersionUID = -3352950252556855349L;

  public String getSelf() {
    return self;
  }

  public void setSelf(String self) {
    this.self = self;
  }

}
