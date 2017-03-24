package mx.infotec.smartcity.backend.service.mail;

public interface MailService {
  
  boolean sendMail(String email, Integer idTemplate);
  
  boolean sendRecoveryMail(String email, String token);

}
