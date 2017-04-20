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

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.utils.Constants;

/**
 *
 * @author Erik Valdivieso
 */
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

        if (token == null) {
            response.sendError(HttpStatus.SC_FORBIDDEN, "Auth required");
        } else {
            try {
                IdentityUser user = loginService.findUserByValidToken(token);
                
                if (user == null) {
                    response.sendError(HttpStatus.SC_FORBIDDEN, "Invalid user");
                } else {
                    servletRequest.setAttribute(Constants.USER_REQUES_KEY, user);
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } catch (Exception ex) {
                LOG.error("Error at userFilter", ex);
                response.sendError(HttpStatus.SC_FORBIDDEN, "Invalid user");
            }
        }
    }

    @Override
    public void destroy() {
        // Do notting
    }

}
