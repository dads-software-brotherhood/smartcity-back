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

import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.SelfRole;


/**
 *
 * @author Benjamin Vander Stichelen
 */
@RestController
@RequestMapping("/user")
public class RoleController {

  @Autowired
  @Qualifier("keystoneRoleService")
  private RoleService roleService;

  @RequestMapping(method = RequestMethod.GET, value = "/roles",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> getUsers(@RequestHeader(value = "token-auth") String token) {
    try {
      return ResponseEntity.accepted().body(roleService.getAllRoles(token));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }


  @RequestMapping(method = RequestMethod.POST, value = "/roles",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> createRole(@RequestHeader(value = "token-auth") String token,
      @RequestBody SelfRole role) {
    try {


      return ResponseEntity.accepted().body(roleService.createRole(role, token));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/roles/{roleid}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> buscarRolePorId(@RequestHeader(value = "token-auth") String authToken,
      @PathVariable("roleid") String roleid) {
    try {
      return ResponseEntity.accepted().body(roleService.getRole(roleid, authToken));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/roles/{name}/byName",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> buscarRolePorNombre(
      @RequestHeader(value = "token-auth") String authToken, @PathVariable("name") String name) {
    try {
      return ResponseEntity.accepted().body(roleService.getRoleByName(name, authToken));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/roles/{roleid}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> deleteRolePorId(@RequestHeader(value = "token-auth") String authToken,
      @PathVariable("roleid") String roleid) {
    try {
      return ResponseEntity.accepted().body(roleService.deleteRole(roleid, authToken));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }

  @RequestMapping(method = RequestMethod.PATCH, value = "/roles/{roleid}",
      consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> updateRolePorId(@RequestHeader(value = "token-auth") String authToken,
      @PathVariable("roleid") String roleid, @RequestBody SelfRole role) {
    try {
      return ResponseEntity.accepted().body(roleService.updateRole(roleid, authToken, role));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
  }


}
