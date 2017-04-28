package mx.infotec.smartcity.backend.filter;

import java.io.IOException;
import java.util.Set;

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

/**
 *
 * @author Erik Valdivieso
 */
public class RoleFilter implements Filter {

    private final Role[] validRoles;

    public RoleFilter(Role... validRoles) {
        if (validRoles == null) {
            this.validRoles = new Role[0];
        } else {
            this.validRoles = validRoles;
        }
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String method = request.getMethod();

        if (!"GET".equals(method)) {
            IdentityUser identityUser = (IdentityUser) request.getAttribute(Constants.USER_REQUES_KEY);

            if (identityUser == null || !isHaveValidRole(identityUser.getRoles())) {
                response.sendError(HttpStatus.SC_UNAUTHORIZED, "Invalid user");
                return;
            }
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    private boolean isHaveValidRole(Set<Role> roles) {
        if (roles != null && !roles.isEmpty()) {
            for (Role role : roles) {
                for (Role validRole : validRoles) {
                    if (validRole == role) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void destroy() {
    }

}
