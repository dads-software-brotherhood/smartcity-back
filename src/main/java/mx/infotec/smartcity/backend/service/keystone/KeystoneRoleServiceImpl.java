package mx.infotec.smartcity.backend.service.keystone;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.infotec.smartcity.backend.service.RoleService;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.Request;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Role;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.RoleAssignments;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.Roles;
import mx.infotec.smartcity.backend.service.keystone.pojo.roles.SelfRole;
import mx.infotec.smartcity.backend.utils.Constants;


/**
 *
 * @author Benjamin Vander Stichelen
 */
@Service("keystoneRoleService")
public class KeystoneRoleServiceImpl implements RoleService {

    private static final long   serialVersionUID = 1L;

    private static final Logger LOGGER           =
            LoggerFactory.getLogger(KeystoneRoleServiceImpl.class);


    @Value("${idm.servers.keystone}")
    private String              keystoneUrl;
    @Value("${idm.default.domain}")
    private String              defaultDomain;

    private String              roleUrl;
    private String              getRoleUrl;
    private String              userRoleDomainUrl;
    private String              getUserRole;

    private String              DBLCUOTE         = "\"";
    private Integer             TIMEOUT          = 90000;
    
    private String roleAssignments;

    @PostConstruct
    protected void init() {
        roleUrl = keystoneUrl + "/v3/roles";
        getRoleUrl = roleUrl + "/%s";
        userRoleDomainUrl = keystoneUrl + "/v3/domains/%s/users/%s/roles/%s";
        getUserRole = keystoneUrl + "/v3/domains/%s/users/%s/roles";
        roleAssignments = keystoneUrl + "/v3/role_assignments?role.id=%s";
    }

    @Override
    public List<Role> getAllRoles(String authToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);
        HttpEntity<Request> requestEntity = new HttpEntity<Request>(headers);
        HttpEntity<Roles> responseEntity =
                restTemplate.exchange(roleUrl, HttpMethod.GET, requestEntity, Roles.class);
        return responseEntity.getBody().getRoles();
    }

    @Override
    public Roles getAllRolesLikeRoles(String authToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);
        HttpEntity<Request> requestEntity = new HttpEntity<Request>(headers);
        HttpEntity<Roles> responseEntity =
                restTemplate.exchange(roleUrl, HttpMethod.GET, requestEntity, Roles.class);
        return responseEntity.getBody();
    }

    @Override
    public SelfRole createRole(SelfRole role, String authToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);
        HttpEntity<SelfRole> requestEntity = new HttpEntity<SelfRole>(role, headers);
        HttpEntity<SelfRole> responseEntity =
                restTemplate.exchange(roleUrl, HttpMethod.POST, requestEntity, SelfRole.class);
        return responseEntity.getBody();
    }

    @Override
    public SelfRole getRole(String idRole, String authToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);
        LOGGER.debug("get role user : {}", String.format(getRoleUrl, idRole));
        HttpEntity<SelfRole> requestEntity = new HttpEntity<SelfRole>(headers);
        HttpEntity<SelfRole> responseEntity = restTemplate.exchange(
                String.format(getRoleUrl, idRole), HttpMethod.GET, requestEntity, SelfRole.class);
        return responseEntity.getBody();
    }

    @Override
    public Roles getRoleUser(String domainId, String userId, String authToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);
        HttpEntity<SelfRole> requestEntity = new HttpEntity<SelfRole>(headers);
        HttpEntity<Roles> responseEntity =
                restTemplate.exchange(String.format(getUserRole, domainId, userId), HttpMethod.GET,
                        requestEntity, Roles.class);
        return responseEntity.getBody();
    }


    @Override
    public Roles getRoleUserDefaultDomain(String userId, String authToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);
        HttpEntity<SelfRole> requestEntity = new HttpEntity<SelfRole>(headers);
        LOGGER.debug("URL: {}", String.format(getUserRole, defaultDomain, userId));
        HttpEntity<Roles> responseEntity =
                restTemplate.exchange(String.format(getUserRole, defaultDomain, userId),
                        HttpMethod.GET, requestEntity, Roles.class);
        return responseEntity.getBody();
    }

    @Override
    public SelfRole getRoleByName(String name, String authToken) {
        List<Role> roles = this.getAllRoles(authToken);
        for (Role role : roles) {
            if (role.getName().equals(name)) {
                return getRole(role.getId(), authToken);
            }
        }
        return new SelfRole();
    }

    @Override
    public SelfRole deleteRole(String roleid, String authToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);
        LOGGER.debug("user url: {}", String.format(getRoleUrl, roleid));
        HttpEntity<SelfRole> requestEntity = new HttpEntity<SelfRole>(headers);
        HttpEntity<SelfRole> responseEntity =
                restTemplate.exchange(String.format(getRoleUrl, roleid), HttpMethod.DELETE,
                        requestEntity, SelfRole.class);
        return responseEntity.getBody();

    }

    @Override
    public SelfRole updateRole(String roleid, String authToken, SelfRole role) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(TIMEOUT);
        requestFactory.setReadTimeout(TIMEOUT);

        restTemplate.setRequestFactory(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);
        HttpEntity<SelfRole> requestEntity = new HttpEntity<SelfRole>(role, headers);
        HttpEntity<SelfRole> responseEntity = restTemplate.exchange(
                String.format(getRoleUrl, roleid), HttpMethod.PATCH, requestEntity, SelfRole.class);
        return responseEntity.getBody();

    }

    @Override
    public void assignRoleToUserOnDomain(String roleId, String userId, String domain,
            String authToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);

        LOGGER.debug("set role: {}", String.format(userRoleDomainUrl, domain, userId, roleId));
        HttpEntity<Request> requestEntity = new HttpEntity<Request>(headers);
        HttpEntity<Roles> responseEntity = restTemplate.exchange(
                String.format(userRoleDomainUrl, domain, userId, roleId.toLowerCase()),
                HttpMethod.PUT, requestEntity, Roles.class);
        // return responseEntity.getBody().getRoles();

    }

    @Override
    public void assignRoleToUserOnDefaultDomain(String roleId, String userId, String authToken) {
        this.assignRoleToUserOnDomain(roleId, userId, defaultDomain, authToken);
    }

    @Override
    public void deleteRoleFromUserOnDomain(String roleId, String userId, String domain,
            String authToken) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-auth-token", authToken);

        LOGGER.debug("set role: {}", String.format(userRoleDomainUrl, domain, userId, roleId));
        HttpEntity<Request> requestEntity = new HttpEntity<Request>(headers);
        HttpEntity<Roles> responseEntity =
                restTemplate.exchange(String.format(userRoleDomainUrl, domain, userId, roleId),
                        HttpMethod.DELETE, requestEntity, Roles.class);


    }

    @Override
    public void deleteRoleFromUserDefaultDomain(String roleId, String userId, String authToken) {
        this.deleteRoleFromUserOnDomain(roleId, userId, defaultDomain, authToken);
    }

    @Override
    public List<RoleAssignments> getUsersByRoleId(String idRole, String authToken) throws ServiceException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.AUTH_TOKEN_HEADER, authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Request> requestEntity = new HttpEntity<>(headers);
        try {
            HttpEntity<Roles> responseEntity = restTemplate.exchange(String.format(roleAssignments, idRole),
                    HttpMethod.GET, requestEntity, Roles.class);
            return responseEntity.getBody().getRoleAssignments();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


}
