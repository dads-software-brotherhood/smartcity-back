package mx.infotec.smartcity.backend.service.keystone.pojo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import mx.infotec.smartcity.backend.service.keystone.pojo.token.Token_;

/**
 *
 * @author Erik Valdivieso
 */
public class Identity implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> methods;
    private Password password;
    //RNM
    private Token_ token;
    
    public Identity() {
    }

    public Identity(User user) {
        this.methods = Arrays.asList("password");
        password = new Password(user);
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Token_ getToken() {
      return token;
    }

    public void setToken(Token_ token) {
      this.token = token;
    }
    
    
}
