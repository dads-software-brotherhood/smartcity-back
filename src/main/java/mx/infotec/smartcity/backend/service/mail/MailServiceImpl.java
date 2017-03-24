package mx.infotec.smartcity.backend.service.mail;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mailService")
public class MailServiceImpl implements MailService {
  
  private JavaMailSender mailSender;
  
  

  @Override
  public boolean sendMail(String email, Integer idTemplate) {
    
    MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
      
      @Override
      public void prepare(MimeMessage mimeMessage) throws Exception {
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("rodrigo.nievez@gmail.com"));
        mimeMessage.setFrom(new InternetAddress("test@localhost.com"));
        mimeMessage.setText("Mensaje de prueba");
        
      }
    };
    mailSender.send(messagePreparator);
    return false;
  }
  
  private String getTemplate(int idTemplate) {
    
    return null;
  }

  @Override
  public boolean sendRecoveryMail(String email, String token) {
    // TODO Auto-generated method stub
    return false;
  }

}
