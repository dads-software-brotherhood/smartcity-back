package mx.infotec.smartcity.backend.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;
import mx.infotec.smartcity.backend.utils.Constants;

/**
 *
 * @author Erik Valdivieso
 */
@Component
public class LoggedUserFilter implements Filter {

  @Autowired
  @Qualifier("keystoneLoginService")
  private LoginService loginService;
  
  static final Logger LOG = LoggerFactory.getLogger(LoggedUserFilter.class);

  @Override
  public void init(FilterConfig fc) throws ServletException {
    // Do notting
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String token = request.getHeader(Constants.AUTH_TOKEN_HEADER);

    if (token == null || !loginService.isValidToken(token)) {
      response.sendError(403, "Invalid user");
    } else {
      // TODO: implementar método para recuperar Usuario en loginService
      try {
        IdentityUser user =  loginService.findUserByValidToken(token);
        servletRequest.setAttribute("userId", user.getName());
        
        filterChain.doFilter(servletRequest, servletResponse);
      } catch (InvalidTokenException e) {
        response.sendError(403, "Invalkd user");
        LOG.error("Error to validate token, cause: ", e);
      }
    }
  }

  @Override
  public void destroy() {
    // Do notting
  }

}
