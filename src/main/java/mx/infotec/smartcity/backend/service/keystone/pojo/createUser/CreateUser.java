
package mx.infotec.smartcity.backend.service.keystone.pojo.createUser;

public class CreateUser {
  
  public CreateUser() {
    
  }
  
  public CreateUser(User_ user) {
    this.user = user;
  }

  private User_ user;

  public User_ getUser() {
    return user;
  }

  public void setUser(User_ user) {
    this.user = user;
  }

}
