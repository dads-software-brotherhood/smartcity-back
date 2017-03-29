package mx.infotec.smartcity.backend.utils;

import java.util.Set;

import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.model.RoleId;

public class RoleUtil {

  private static RoleUtil    instance;
  private static Set<RoleId> roles;

  private RoleUtil() {

  }

  private RoleUtil(Set<RoleId> roles) {
    RoleUtil.roles = roles;
  }

  public static RoleUtil getInstance() {
    if (instance == null) {
      instance = new RoleUtil();
    }
    return instance;
  }

  public static RoleUtil init(Set<RoleId> roles) {
    if (instance == null) {
      instance = new RoleUtil(roles);
    }
    return instance;
  }

  public static Role validateRole(String name) {
    for (RoleId role : roles) {
      if (role.getRole().name().equals(name)) {
        return role.getRole();
      }
    }
    return null;
  }

  public String getIdRole(Role role) {
    for (RoleId roleId : roles) {
      if (roleId.getRole().name().equals(role.name())) {
        return roleId.getId();
      }
    }
    return null;
  }



}
