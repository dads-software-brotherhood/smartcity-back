package mx.infotec.smartcity.backend.filter;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Role;
import mx.infotec.smartcity.backend.utils.Constants;

public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uri = httpRequest.getRequestURI();

        IdentityUser loggedUser =
                (IdentityUser) httpRequest.getAttribute(Constants.USER_REQUES_KEY);

        if (loggedUser == null) {
            httpResponse.sendError(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            Role defaultRole = loggedUser.getRoles().iterator().next();
            switch (defaultRole) {
                case ADMIN:

                    if (uri.contains(Constants.URL_GROUPS) || uri.contains(Constants.URL_RULES)) {
                        httpResponse.sendError(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
                    } else {
                        filterChain.doFilter(request, response);
                    }

                    break;
                case TRANSPORT_ADMIN:

                    if (!uri.contains(Constants.URL_TRANSPORT)) {
                        httpResponse.sendError(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
                    } else {
                        filterChain.doFilter(request, response);
                    }

                    break;
                case USER:

                    if (!uri.contains(Constants.URL_PROFILE)) {
                        httpResponse.sendError(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
                    } else {
                        filterChain.doFilter(request, response);
                    }

                    break;
                case SA:
                    filterChain.doFilter(request, response);                    
                    break;
                default:
                    break;
            }
            
            
        }

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
