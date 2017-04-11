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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.exception.InvalidTokenException;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.utils.Constants;

public class SelfDataEditFilter implements Filter {

    @Autowired
    @Qualifier("keystoneLoginService")
    private LoginService loginService;

    @Autowired
    private UserProfileRepository profileRepository;
    
    static final Logger  LOG = LoggerFactory.getLogger(SelfDataEditFilter.class);
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        IdentityUser loggedUser =
                (IdentityUser) httpRequest.getAttribute(Constants.USER_REQUES_KEY);
        String token = httpRequest.getHeader(Constants.AUTH_TOKEN_HEADER);

        if (token == null || !loginService.isValidToken(token)) {
            httpResponse.sendError(403, "Auth required");
        } else {
            Pattern pattern = Pattern.compile("^[a-z0-9]*");
            Matcher matcher;
            String id="";
            String[] dataUri = httpRequest.getRequestURI().split("/");
            for (int i = 0; i < dataUri.length; i ++) {
                String matcherValue = dataUri[i];
                if (matcherValue.isEmpty()) {
                    continue;
                }
                matcher = pattern.matcher(matcherValue);
                if (matcher.matches()) {
                    id = matcherValue;
                    break;
                }
            }
            if (loggedUser == null) {
                try {
                    loggedUser = loginService.findUserByValidToken(token);
                } catch (ServiceException e) {
                    httpResponse.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal error");
                    LOG.error("Error to validate token, casue:", e);
                } catch (InvalidTokenException e) {
                    httpResponse.sendError(403, "Invalid user");
                    LOG.error("Error to validate token, casue:", e);
                }
            }
            
            try {
                UserProfile profile = profileRepository.findOne(id);
                
                if (!loggedUser.getId().equals(profile.getKeystoneId())) {
                    httpResponse.sendError(HttpStatus.SC_UNAUTHORIZED,"Unauthorized");
                }
            } catch (Exception e) {
                httpResponse.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Internal error");
            }
            
           
                     
        }


    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
