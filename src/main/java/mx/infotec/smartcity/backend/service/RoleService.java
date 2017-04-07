package mx.infotec.smartcity.backend.service;

import java.io.Serializable;
import java.util.List;

import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Role;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.RoleAssignments;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Roles;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.SelfRole;

/**
 *
 * @author Erik Valdivieso
 */
public interface RoleService extends Serializable {

  // Get all Roles
  List<Role> getAllRoles(String authToken);

  // Create a Role
  SelfRole createRole(SelfRole role, String token);

  // Get a Role by id
  SelfRole getRole(String idRole, String authToken);

  // Lookup role by name
  SelfRole getRoleByName(String name, String authToken);

  // Delete a Role
  SelfRole deleteRole(String roleid, String authToken);

  // Change Role
  SelfRole updateRole(String roleid, String authToken, SelfRole role);


  // Get roles of a user
  Roles getRoleUser(String domainId, String userId, String authToken);

  // Assign user role to domain
  void assignRoleToUserOnDomain(String roleId, String userId, String domain, String authToken);

  // Delete Role of a user on domain
  void deleteRoleFromUserOnDomain(String roleId, String userId, String domain, String authToken);

  void assignRoleToUserOnDefaultDomain(String roleId, String userId, String authToken);

  void deleteRoleFromUserDefaultDomain(String roleId, String userId, String authToken);

  Roles getAllRolesLikeRoles(String authToken);

  Roles getRoleUserDefaultDomain(String userId, String authToken);
  
  List<RoleAssignments>  getUsersByRoleId(String idRole, String authToken) throws ServiceException;
  
  


}
