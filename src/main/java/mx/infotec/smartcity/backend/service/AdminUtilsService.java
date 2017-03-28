package mx.infotec.smartcity.backend.service;

import java.io.Serializable;

import mx.infotec.smartcity.backend.service.exception.ServiceException;

public interface AdminUtilsService extends Serializable {
  
  String getAdmintoken() throws ServiceException;
  
  boolean isAdmin(String token) throws ServiceException;

}
