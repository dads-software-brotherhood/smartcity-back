package mx.infotec.smartcity.backend.service.mail;

import mx.infotec.smartcity.backend.model.Email;
import mx.infotec.smartcity.backend.utils.TemplatesEnum;

public interface MailService {
  
  boolean sendMail(TemplatesEnum templateId, Email email);

}
