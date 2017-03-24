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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.infotec.smartcity.backend.service.LoginService;
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
      response.sendError(403);
    } else {
      // TODO: implementar método para recuperar Usuario en loginService
      // loginService.findUserByValidToken(token);
      servletRequest.setAttribute("userId", "idm");
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void destroy() {
    // Do notting
  }

}
