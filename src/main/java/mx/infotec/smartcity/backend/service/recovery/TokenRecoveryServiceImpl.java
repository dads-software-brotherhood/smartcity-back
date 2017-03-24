package mx.infotec.smartcity.backend.service.recovery;

import java.util.Date;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import mx.infotec.smartcity.backend.model.TokenRecovery;
import mx.infotec.smartcity.backend.model.TokenRequest;
import mx.infotec.smartcity.backend.persistence.TokenRecoveryRepository;
import mx.infotec.smartcity.backend.service.AdminUtilsService;
import mx.infotec.smartcity.backend.service.UserService;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.keystone.pojo.changePassword.ChangeUserPassword;
import mx.infotec.smartcity.backend.service.keystone.pojo.changePassword.User_;
import mx.infotec.smartcity.backend.service.keystone.pojo.createUser.CreateUser;
import mx.infotec.smartcity.backend.service.keystone.pojo.user.User;
import mx.infotec.smartcity.backend.service.mail.MailService;


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

  @Override
  public TokenRecovery generateTocken(String email, String idUser) throws ServiceException {
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
      TokenRecovery recovery = tokenRepository.findById(tokenRecovery);
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

    String adminToken = adminUtils.getAdmintoken();
    try {
      User user = userService.getUserByName(email, adminToken);
      if (user == null) {
        return false;
      }
      TokenRecovery recovery = generateTocken(email, user.getId());
      LOG.info("TokenRecovery:  " +  recovery.getId());
      //mailService.sendMail(email, null);
      return true;
    } catch (Exception e) {
      LOG.error("Error trying to recovery password, cause: ", e);
      throw new ServiceException(e);
    }
  }

  @Override
  public boolean updatePassword(String tokenRecovery, TokenRequest request)
      throws ServiceException {
    try {
      if (validateTokenRecovery(tokenRecovery)) {
        TokenRecovery recovery = tokenRepository.findById(tokenRecovery);
        String adminToken = adminUtils.getAdmintoken();
        CreateUser createUser = userService.getUser(recovery.getIdUser(), adminToken);
        User_ user = new User_();
        user.setOriginalPassword(createUser.getUser().getPassword());
        user.setPassword(new String(request.getPassword()));
        ChangeUserPassword password = new ChangeUserPassword();
        password.setUser(user);
        userService.changePassword(recovery.getIdUser(), password, adminToken);
        return true;
      }
      return false;
    } catch (Exception e) {
      LOG.error("Error trying to update password recovery, cause: ", e);
      throw new ServiceException(e);

    }

  }

}
