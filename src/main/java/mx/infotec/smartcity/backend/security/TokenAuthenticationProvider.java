package mx.infotec.smartcity.backend.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;



@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

  private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

  @Autowired
  @Qualifier("keystoneLoginService")
  LoginService                loginService;

  @Autowired
  UserService                 userService;

  @Autowired
  RoleService                 roleService;

  @Value("${idm.admin.username}")
  private String              adminUser;


  @Value("${idm.admin.password}")
  private String              adminPassword;

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    if (auth.isAuthenticated()) {
      return auth;
    }
    logger.debug("ENTER IN HEADER AuthenticationTokenFilter TOKEN : ");

    String token = auth.getCredentials().toString();
    if (loginService.isValidToken(token)) {


      String tokenAdmin;
      try {
        tokenAdmin = loginService.performLogin(adminUser, adminPassword.toCharArray())
            .getTokenInfo().getToken();
      } catch (InvalidCredentialsException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        throw new BadCredentialsException("Invalid token " + token);
      }
      auth = new PreAuthenticatedAuthenticationToken(
          userService.getUserFromToken(tokenAdmin, token).getToken().getUser(), token);
      auth.setAuthenticated(true);
      logger.debug("Token authentication. Token: ");
    } else {
      throw new BadCredentialsException("Invalid token " + token);
    }
    return auth;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return true;
  }

}
