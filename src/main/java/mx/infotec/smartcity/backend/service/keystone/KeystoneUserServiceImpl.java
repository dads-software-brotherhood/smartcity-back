package mx.infotec.smartcity.backend.service.keystone;


import java.util.List;
import javax.annotation.PostConstruct;
import mx.infotec.smartcity.backend.service.keystone.pojo.Group;
import mx.infotec.smartcity.backend.service.keystone.pojo.Project;
import mx.infotec.smartcity.backend.service.keystone.pojo.User;
import mx.infotec.smartcity.backend.service.UserService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Benjamin Vander Stichelen
 */
@Service("keystoneUserService")
public class KeystoneUserServiceImpl implements UserService {

    private static final long serialVersionUID = 1L;

    @Value("${idm.servers.keystone}")
    private String keystonUrl;

    private String userUrl;

    @PostConstruct
    protected void init() {
        userUrl = keystonUrl + "/v3/users";
    }

	@Override
	public List<User> getAllUsers(String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createUser(User user, String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String idUser, String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(String idUser, String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUser(String idUser, String authToken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Group getUserGroups(String idUser, String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project getUserProjects(String idUser, String authToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changePassword(String idUser, String authToken, String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
