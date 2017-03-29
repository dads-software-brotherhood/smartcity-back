package mx.infotec.smartcity.backend.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Roles;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.SelfRole;

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
    String adminToken = "";
    LOGGER.info("Starting the aplication event");

    try {
      adminToken = adminUtils.getAdmintoken();
      Roles roles = roleService.getAllRolesLikeRoles(adminToken);
      for (Role role : Role.values()) {
        Boolean create = true;
        for (mx.infotec.smartcity.backend.service.keystone.pojo.roles.Role roleKey : roles
            .getRoles()) {
          if (roleKey.getName().equals(role.name())) {
            create = false;
          }

        }
        if (create) {
          LOGGER.debug("It will be create the role {}", role.name());
          SelfRole newRole = new SelfRole(role.name());
          roleService.createRole(newRole, adminToken);
        }
      }

    } catch (ServiceException e) {
      LOGGER.error("Error at roles creation", e);
    } finally {
      if (adminToken != null && !adminToken.equals("")) {
        loginService.invalidToken(adminToken);
      }
    }


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
