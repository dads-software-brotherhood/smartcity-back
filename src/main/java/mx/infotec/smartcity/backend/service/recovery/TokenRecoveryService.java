package mx.infotec.smartcity.backend.service.recovery;

import mx.infotec.smartcity.backend.model.TokenRecovery;
import mx.infotec.smartcity.backend.model.TokenRequest;
import mx.infotec.smartcity.backend.service.exception.ServiceException;

public interface TokenRecoveryService {
  
  TokenRecovery generateToken(String email, String idUser) throws ServiceException;
  
  boolean validateTokenRecovery(String tokenRecovery) throws ServiceException;
  
  boolean recoveryPassword(String email) throws ServiceException;
  
  boolean updatePassword(String tokenRecovery, TokenRequest request) throws ServiceException;
  
  TokenRecovery getTokenById(String token) throws ServiceException;
  
  void deleteById(String id);
  void deleteAllByEmail(String email);
  
  void deleteExpiredToken();
 
}
