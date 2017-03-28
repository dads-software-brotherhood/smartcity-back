
package mx.infotec.smartcity.backend.service.keystone.pojo.token;

import java.io.Serializable;

public class Token implements Serializable {

  private Token_            token;

  private Error             error;
  
  private final static long serialVersionUID = -768370041286948253L;

  public Token_ getToken() {
    return token;
  }

  public void setToken(Token_ token) {
    this.token = token;
  }

  public Error getError() {
    return error;
  }

  public void setError(Error error) {
    this.error = error;
  }

}
