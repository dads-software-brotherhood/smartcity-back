package mx.infotec.smartcity.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.keystone.pojo.changePassword.ChangeUserPassword;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;


/**
 *
 * @author Benjamin Vander Stichelen
 */
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  @Qualifier("keystoneUserService")
  private UserService userService;

  /*
   * @RequestMapping(method = RequestMethod.POST, value = "/token", consumes =
   * MediaType.APPLICATION_JSON_UTF8_VALUE) public ResponseEntity<?> getToken(@RequestBody
   * TokenRequest tokenRequest) { try { return
   * ResponseEntity.accepted().body(loginService.performLogin(tokenRequest.getUsername(),
   * tokenRequest.getPassword())); } catch (Exception ex) { return
   * ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password invalid"); } }
   */

  @RequestMapping(method = RequestMethod.GET, value = "/users",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> getUsers(@RequestHeader(value = "token-auth") String token) {
    try {
      return ResponseEntity.accepted().body(userService.getAllUsers(token));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }


  @RequestMapping(method = RequestMethod.POST, value = "/users",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createUsers(@RequestHeader(value = "token-auth") String token,
      @RequestBody CreateUser user) {
    try {

      // return ResponseEntity.accepted().body(user);
      return ResponseEntity.accepted().body(userService.createUser(user, token));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "/users/{userid}/password",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> changeUserPassword(@PathVariable("userid") String userid,
      @RequestHeader(value = "token-auth") String token, @RequestBody ChangeUserPassword user) {
    try {

      // return ResponseEntity.accepted().body(user);
      return ResponseEntity.accepted().body(userService.changePassword(userid, user, token));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.PATCH, value = "/users/{userid}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> updateUser(@PathVariable("userid") String userid,
      @RequestHeader(value = "token-auth") String token, @RequestBody CreateUser user) {
    try {

      // return ResponseEntity.accepted().body(user);
      return ResponseEntity.accepted().body(userService.updateUser(userid, token, user));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/users/{userid}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> getUser(@PathVariable("userid") String userid,
      @RequestHeader(value = "token-auth") String token) {
    try {

      // return ResponseEntity.accepted().body(user);
      return ResponseEntity.accepted().body(userService.getUser(userid, token));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/users/{username}/byUsername",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username,
      @RequestHeader(value = "token-auth") String token) {
    try {

      // return ResponseEntity.accepted().body(user);
      return ResponseEntity.accepted().body(userService.getUserByUsername(username, token));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }


  @RequestMapping(method = RequestMethod.GET, value = "/users/{name}/byName",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> getUserByName(@PathVariable("name") String name,
      @RequestHeader(value = "token-auth") String token) {
    try {

      // return ResponseEntity.accepted().body(user);
      return ResponseEntity.accepted().body(userService.getUserByName(name, token));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }



}
