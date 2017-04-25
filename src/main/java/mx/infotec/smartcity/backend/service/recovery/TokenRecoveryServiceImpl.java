package mx.infotec.smartcity.backend.service.recovery;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.infotec.smartcity.backend.model.Email;
import mx.infotec.smartcity.backend.model.TokenRecovery;
import mx.infotec.smartcity.backend.model.TokenRequest;
import mx.infotec.smartcity.backend.model.UserProfile;
import mx.infotec.smartcity.backend.persistence.TokenRecoveryRepository;
import mx.infotec.smartcity.backend.persistence.UserProfileRepository;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.LoginService;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.User;
import mx.infotec.smartcity.backend.service.mail.MailService;
import mx.infotec.smartcity.backend.utils.Constants;
import mx.infotec.smartcity.backend.utils.TemplatesEnum;


@Service("recoveryService")
public class TokenRecoveryServiceImpl implements TokenRecoveryService {

  private static final Logger LOG = LoggerFactory.getLogger(TokenRecoveryServiceImpl.class);

  @Autowired
  TokenRecoveryRepository     tokenRepository;

  @Autowired
  AdminUtilsService           adminUtils;

  @Autowired
  @Qualifier("keystoneUserService")
  UserService                 userService;

  @Autowired
  MailService                 mailService;
  
  @Autowired
  @Qualifier("keystoneLoginService")
  private LoginService loginService;

  @Autowired
  UserProfileRepository profileRepository;
  
  @Override
  public TokenRecovery generateToken(String email, String idUser) throws ServiceException {
    TokenRecovery recovery = new TokenRecovery();
    recovery.setIdUser(idUser);
    recovery.setEmail(email);
    recovery.setRegisterDate(new Date(new java.util.Date().getTime()));
    try {
      tokenRepository.save(recovery);
      return recovery;

    } catch (Exception e) {
      LOG.error("Error to save new TokenRecovery, cause: ", e);
      throw new ServiceException(e);
    }
  }

  @Override
  public boolean validateTokenRecovery(String tokenRecovery) throws ServiceException {
    try {
      TokenRecovery recovery = tokenRepository.findOne(tokenRecovery);
      if (recovery == null) {
        return false;
      }
      Date compareDate = new Date(recovery.getRegisterDate().getTime());
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(compareDate);
      calendar.add(Calendar.DATE, 1);
      compareDate = calendar.getTime();
      return recovery.getRegisterDate().before(compareDate);

    } catch (Exception e) {
      LOG.error("Error to validate token recovery, cause: ", e);
      throw new ServiceException(e);
    }
  }

  @Override
  public boolean recoveryPassword(String email) throws ServiceException {
    Email emailObj = new Email();
    String adminToken = null;
    try {
      adminToken = adminUtils.getAdmintoken();
      User user = userService.getUserByName(email, adminToken);
      UserProfile userProfile = profileRepository.findByEmail(email);
      if (user == null) {
        return false;
      } else {
        TokenRecovery recovery = generateToken(email, user.getId());
        Map<String, Object> otherParams = new HashMap<>();
        otherParams.put(Constants.GENERAL_PARAM_NAME, getName(userProfile));
        emailObj.setTo(email);
        emailObj.setMessage(recovery.getId());
        emailObj.setContent(otherParams);
        LOG.info("TokenRecovery:  {}", recovery.getId());
        mailService.sendMail(TemplatesEnum.RECOVERY_PASSWORD_EMAIL, emailObj);
        return true;
      }
    } catch (Exception e) {
      LOG.error("Error trying to recovery password, cause: ", e);
      throw new ServiceException(e);
    } finally {
      if(adminToken != null)
        loginService.invalidToken(adminToken);
    }
  }

    private String getName(UserProfile profile) {
        if (profile == null || profile.getName() == null) {
            return "User";
        } else if (profile.getFamilyName() == null) {
            return profile.getName();
        } else {
            return String.format("%s %s", profile.getName(), profile.getFamilyName());
        }
    }
    
  @Override
  public boolean updatePassword(String tokenRecovery, TokenRequest request)
      throws ServiceException {
    String adminToken = null;
    try {
      if (validateTokenRecovery(tokenRecovery)) {
        TokenRecovery recovery = tokenRepository.findOne(tokenRecovery);
        adminToken = adminUtils.getAdmintoken();
        CreateUser createUser = userService.getUser(recovery.getIdUser(), adminToken);
        createUser.getUser().setPassword(new String(request.getPassword()));
        createUser.getUser().setEnabled(true);
        createUser = userService.updateUser(recovery.getIdUser(), adminToken, createUser);
        tokenRepository.deleteTokenRecoveryByEmail(recovery.getEmail());
        return true;
      }
      return false;
    } catch (Exception e) {
      LOG.error("Error trying to update password recovery, cause: ", e);
      throw new ServiceException(e);

    } finally {
      if(adminToken != null)
        loginService.invalidToken(adminToken);
    }

  }

  @Override
  public TokenRecovery getTokenById(String token) throws ServiceException {
    try {
      return tokenRepository.findOne(token);
      
    } catch(Exception e) {
      LOG.error("Error trying to get TokenRecovery, cause: ", e);
      throw new ServiceException(e);
    }
  }

    @Override
    public void deleteById(String id) {
        tokenRepository.delete(id);
    }

    @Override
    public void deleteAllByEmail(String email) {
        tokenRepository.deleteTokenRecoveryByEmail(email);
    }

    @Override
    public void deleteExpiredToken() {
                
        List<TokenRecovery> tokens = tokenRepository.findAll();
        List<TokenRecovery> tokensToDelete = new ArrayList<>();
        
        if (tokens != null && tokens.size() > 0) {
            for (TokenRecovery token : tokens) {
                Date compareDate = new Date(token.getRegisterDate().getTime());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(compareDate);
                calendar.add(Calendar.DATE, 1);
                compareDate = calendar.getTime();
                
                Date now = new Date();
                if (compareDate.before(now)) {
                    tokensToDelete.add(token);
                }
                
            }
        }
        
        if (tokensToDelete.size() > 0) {
            tokenRepository.delete(tokensToDelete);
        }
    }

}
