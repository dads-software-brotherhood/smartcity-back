package mx.infotec.smartcity.backend.utils;

import mx.infotec.smartcity.backend.model.Role;

public class RoleUtil {


  public static Role validateRole(String name) {
    for (Role role : Role.values()) {
      if (role.name().equals(name)) {
        return role;
      }
    }
    return null;
  }


}
