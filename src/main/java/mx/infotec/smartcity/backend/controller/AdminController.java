package mx.infotec.smartcity.backend.controller;

import java.util.List;

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
      if (!keystoneUserService.isRegisteredUser(model.getEmail())) {

        if (keystoneUserService.createUserByAdmin(model)) {
          return ResponseEntity.status(HttpStatus.ACCEPTED).body("Success");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
      }
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Registered User");
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }
  
  @RequestMapping(value = "/user/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> deleteUser(@RequestBody UserModel model) {
    try {
      if (keystoneUserService.isRegisteredUser(model.getEmail())){

        if (keystoneUserService.deleteUserByAdmin(model)) {
          return ResponseEntity.status(HttpStatus.ACCEPTED).body("Success");
        }

      }
      return ResponseEntity.status(HttpStatus.CONFLICT).body("User Not Fond");
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }

  @RequestMapping(value = "/user/list", method = RequestMethod.GET)
  public ResponseEntity<?> userList() {
    try {
      List<UserModel> userModelList = keystoneUserService.getUserModelList();
      if (userModelList != null && !userModelList.isEmpty()) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userModelList);
      }
      
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Users Not Found");
    } catch (
    ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }
  
  @RequestMapping(value = "/user/filter", method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> userTry(@RequestBody UserModel model) {
    try {
      if (!keystoneUserService.isRegisteredUser(model.getEmail())) {

        if (keystoneUserService.createUserByAdmin(model)) {
          return ResponseEntity.status(HttpStatus.ACCEPTED).body("Success");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
      }
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Registered User");
    } catch (ServiceException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }
}
