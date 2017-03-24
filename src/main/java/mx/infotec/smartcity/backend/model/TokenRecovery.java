package mx.infotec.smartcity.backend.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;

public class TokenRecovery implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Id
  private String id;
  
  private String idUser;
  
  private String email;
  
  private Date registerDate;
  
  public TokenRecovery() {
    
  }

  public TokenRecovery(String idUser, String email, Date registerDate) {
    super();
    this.idUser = idUser;
    this.email = email;
    this.registerDate = registerDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getIdUser() {
    return idUser;
  }

  public void setIdUser(String idUser) {
    this.idUser = idUser;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }
  
  

}
