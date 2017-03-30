package mx.infotec.smartcity.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.smartcity.backend.model.UserModel;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.User_;
import mx.infotec.smartcity.backend.utils.TemplatesEnum;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private UserService keystoneUserService;

  @RequestMapping(value = "/user/register", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> userRegistration(@RequestBody UserModel model) {
    try {
      User_ user = new User_();
      user.setPassword("");
      user.setName(model.getEmail());
      CreateUser createUser = new CreateUser(user);
      if (!keystoneUserService.isRegisteredUser(user.getName())
          && keystoneUserService.createUserAndSendMail(createUser, TemplatesEnum.MAIL_SAMPLE)) {

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Success");
      }
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Registered User");
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }
}
