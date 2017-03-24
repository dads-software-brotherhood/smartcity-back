package mx.infotec.smartcity.backend.service.recovery;

import mx.infotec.smartcity.backend.model.TokenRecovery;
import mx.infotec.smartcity.backend.model.TokenRequest;
import mx.infotec.smartcity.backend.service.exception.ServiceException;

public interface TokenRecoveryService {
  
  TokenRecovery generateTocken(String email, String idUser) throws ServiceException;
  
  boolean validateTokenRecovery(String tokenRecovery) throws ServiceException;
  
  boolean recoveryPassword(String email) throws ServiceException;
  
  boolean updatePassword(String tokenRecovery, TokenRequest request) throws ServiceException;
 
}
