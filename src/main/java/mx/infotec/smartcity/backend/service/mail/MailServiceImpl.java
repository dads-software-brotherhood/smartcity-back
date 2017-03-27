package mx.infotec.smartcity.backend.service.mail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.utils.Constants;
import mx.infotec.smartcity.backend.utils.TemplatesEnum;

@Service
@Qualifier("mailService")
public class MailServiceImpl implements MailService {

//  @Autowired
//  private MailSender mailsernder;
  
  private JavaMailSender mailSender;
  
  @Autowired
  public MailServiceImpl(JavaMailSender mailSender) {
      this.mailSender = mailSender;
  }
  
  @Autowired
  private Configuration freemarkerConfiguration;

  @Value(value = "${spring.mail.from}")
  private String     from;
  

  @Override
  public boolean sendMail(String email, TemplatesEnum templateId) {
    
    MimeMessagePreparator preparator = new MimeMessagePreparator() {
      
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        mimeMessage.setSubject("notification");
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("rodrigo.nievez@geekearte.com"));
        mimeMessage.setFrom(new InternetAddress(from));
        mimeMessage.setText(getTemplate(templateId), Constants.ENCODING, Constants.FORTMAT_TEXT_HTML);
        
      }
    };
    try {
      this.mailSender.send(preparator);
      return true;
    } catch (Exception e) {
      e.getStackTrace();
    }
    
    return false;
  }
  

  @GetMapping("/")
  private String getTemplate(TemplatesEnum templateEnum) throws ServiceException{
    Map<String, Object> values = new HashMap<>();
    try {
      Template template = freemarkerConfiguration.getTemplate(templateEnum.value(), Constants.ENCODING);
      switch (templateEnum) {
        case MAIL_SAMPLE:
          values.put("from", "test");
          values.put("to", "email.test");
          values.put("message", "messageTest");
          
          break;

        default:
          break;
      }
      return FreeMarkerTemplateUtils.processTemplateIntoString(template, values);
    } catch (IOException | TemplateException e) {
      throw new ServiceException(e);
    }
    
  }



}
