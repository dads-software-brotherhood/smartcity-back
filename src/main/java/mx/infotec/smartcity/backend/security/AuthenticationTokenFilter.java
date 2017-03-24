package mx.infotec.smartcity.backend.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.InvalidCredentialsException;

@Component

public class AuthenticationTokenFilter implements Filter {

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


  private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

  @Override
  public void init(FilterConfig fc) throws ServletException {
    logger.info("Init AuthenticationTokenFilter");
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
      throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.getContext();
    if (context.getAuthentication() != null && context.getAuthentication().isAuthenticated()) { // do
      setHeader(req, res, fc);// nothing
    } else {
      setHeader(req, res, fc);
    }


  }

  private void setHeader(ServletRequest req, ServletResponse res, FilterChain fc)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) req;
    String token = httpRequest.getHeader("token-auth");

    logger.info("ENTER IN HEADER AuthenticationTokenFilter TOKEN : " + token);
    if (token != null) {
      MutableHttpServletRequest wrapper = new MutableHttpServletRequest(httpRequest);
      Authentication auth = new TokenAuthentication(token);

      SecurityContextHolder.getContext().setAuthentication(auth);
      String tokenAdmin;
      try {
        tokenAdmin = loginService.performLogin(adminUser, adminPassword.toCharArray())
            .getTokenInfo().getToken();
      } catch (InvalidCredentialsException e) {
        throw new BadCredentialsException("Invalid token " + token);
      }

      req.setAttribute("id",
          userService.getUserFromToken(tokenAdmin, token).getToken().getUser().getId());
      fc.doFilter(req, res);
    } else {
      req.setAttribute("id", "0");

      fc.doFilter(req, res);
    }
  }

  @Override
  public void destroy() {

  }
}
