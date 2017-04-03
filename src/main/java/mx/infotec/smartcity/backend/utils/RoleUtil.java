package mx.infotec.smartcity.backend.utils;

import java.util.HashMap;

import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.model.RoleId;

public class RoleUtil {

  private static RoleUtil                instance;
  private static HashMap<String, RoleId> roles;

  private RoleUtil() {

  }

  private RoleUtil(HashMap<String, RoleId> roles) {
    RoleUtil.roles = roles;
  }

  public static RoleUtil getInstance() {
    if (instance == null) {
      instance = new RoleUtil();
    }
    return instance;
  }

  public static RoleUtil init(HashMap<String, RoleId> roles) {
    if (instance == null) {
      instance = new RoleUtil(roles);
    }
    return instance;
  }

  public static Role validateRole(String name) {
    return roles.get(name).getRole();
  }

  public String getIdRole(Role role) {
    return roles.get(role.name()).getId();
  }



}
