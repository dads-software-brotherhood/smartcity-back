package mx.infotec.smartcity.backend.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.utils.Constants;

public class SelfDataEditFilter implements Filter {

    static final Logger LOG = LoggerFactory.getLogger(SelfDataEditFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        IdentityUser loggedUser
                = (IdentityUser) httpRequest.getAttribute(Constants.USER_REQUES_KEY);

        //LoggedUser prefilter required
        if (loggedUser == null) {
            httpResponse.sendError(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            Pattern pattern = Pattern.compile("^[a-z0-9]*");
            Matcher matcher;
            String id = "";
            String[] dataUri = httpRequest.getRequestURI().split("/");
            
            for (String matcherValue : dataUri) {
                if (matcherValue.isEmpty()) {
                    continue;
                }
                matcher = pattern.matcher(matcherValue);
                if (matcher.matches()) {
                    id = matcherValue;
                    break;
                }
            }

            if (loggedUser.getMongoId() != null && loggedUser.getMongoId().equals(id)) {
                filterChain.doFilter(request, response);
            } else {
                httpResponse.sendError(HttpStatus.SC_UNAUTHORIZED, "Unauthorized");
            }

        }

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
