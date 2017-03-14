package mx.infotec.smartcity.backend.service;

import java.io.Serializable;
import java.util.List;

import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Role;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Roles;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.SelfRole;

/**
 *
 * @author Erik Valdivieso
 */
public interface RoleService extends Serializable {

  List<Role> getAllRoles(String authToken);

  SelfRole createRole(SelfRole role, String token);

  SelfRole getRole(String idRole, String authToken);

  SelfRole getRoleByName(String name, String authToken);

  SelfRole deleteRole(String roleid, String authToken);

  SelfRole updateRole(String roleid, String authToken, SelfRole role);

  void assignRoleToUserOnDefaultDomain(String roleId, String userId, String domain,
      String authToken);

  Roles getRoleUser(String domainId, String userId, String authToken);


}
