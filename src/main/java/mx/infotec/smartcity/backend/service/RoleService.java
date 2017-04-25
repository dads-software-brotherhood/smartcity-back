package mx.infotec.smartcity.backend.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
  void assignRoleToUserOnProject(String roleId, String userId, String project, String authToken);

  // Delete Role of a user on domain
  void deleteRoleFromUserOnProject(String roleId, String userId, String domain, String authToken);

  void assignRoleToUserOnDefaultProject(String roleId, String userId, String authToken);

  void deleteRoleFromUserDefaultProject(String roleId, String userId, String authToken);

  Roles getAllRolesLikeRoles(String authToken);

  Roles getRoleUserDefaultProject(String userId, String authToken);
  
  List<RoleAssignments>  getUsersByRoleIdOnDefaultProject(String idRole, String authToken) throws ServiceException;
  
  Set<String> getUserIdmIdByRole(String idRole, String authToken);

}
