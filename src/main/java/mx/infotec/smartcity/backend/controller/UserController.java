package mx.infotec.smartcity.backend.controller;

import mx.infotec.smartcity.backend.model.TokenRequest;
import mx.infotec.smartcity.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(method = RequestMethod.POST, value = "/token", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getToken(@RequestBody TokenRequest tokenRequest) {
        try {
            return ResponseEntity.accepted().body(loginService.performLogin(tokenRequest.getUsername(), tokenRequest.getPassword()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password invalid");
        }
    }
*/
}
