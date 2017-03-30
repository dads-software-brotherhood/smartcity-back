package mx.infotec.smartcity.backend.listener;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.model.RoleId;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Roles;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.SelfRole;
import mx.infotec.smartcity.backend.utils.RoleUtil;

@Component
public class CreateRolesAplicationListener implements ApplicationListener<ApplicationReadyEvent> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationListener.class);

  @Autowired
  @Qualifier("adminUtils")
  private AdminUtilsService   adminUtils;

  @Autowired
  @Qualifier("keystoneLoginService")
  private LoginService        loginService;

  @Autowired
  @Qualifier("keystoneRoleService")
  private RoleService         roleService;

  public void createRoles() {
//    String adminToken = "";
//    LOGGER.info("Starting the aplication event");
//
//    try {
//      adminToken = adminUtils.getAdmintoken();
//      Roles roles = roleService.getAllRolesLikeRoles(adminToken);
//      Set<RoleId> rolesId = new HashSet<RoleId>();
//      for (Role role : Role.values()) {
//        Boolean create = true;
//        for (mx.infotec.smartcity.backend.service.keystone.pojo.roles.Role roleKey : roles
//            .getRoles()) {
//          if (roleKey.getName().equals(role.name())) {
//            create = false;
//
//            rolesId.add(new RoleId(role, roleKey.getId()));
//          }
//
//        }
//        if (create) {
//          LOGGER.debug("It will be create the role {}", role.name());
//          SelfRole newRole = new SelfRole(role.name());
//          newRole = roleService.createRole(newRole, adminToken);
//          rolesId.add(new RoleId(role, newRole.getRole().getId()));
//        }
//      }
//      RoleUtil.init(rolesId);
//
//    } catch (ServiceException e) {
//      LOGGER.error("Error at roles creation", e);
//    } finally {
//      if (adminToken != null && !adminToken.equals("")) {
//        loginService.invalidToken(adminToken);
//      }
//    }


  }

  /*
   * @Override public void run(String... args) throws Exception { createRoles();
   * 
   * }
   */
  @Override
  public void onApplicationEvent(ApplicationReadyEvent arg0) {
    createRoles();

  }

}
