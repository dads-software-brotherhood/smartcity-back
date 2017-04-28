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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import javax.annotation.PostConstruct;
import mx.infotec.smartcity.backend.model.Email;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.utils.Constants;
import mx.infotec.smartcity.backend.utils.TemplatesEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;

@Service
@Qualifier("mailService")
public class MailServiceImpl implements MailService {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    private JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    private Configuration freemarkerConfiguration;

    @Value(value = "${spring.mail.from}")
    private String from;

    @Value(value = "${front.url}")
    private String frontUrl;
    
    @Value(value = "${front.image-url}")
    private String frontImageUrl;
    
    @Value("${spring.mail.active}")
    private boolean active;
    
    @Value(value = "spring.mail.support")
    private String supportEmail;
        
    @Override
    public boolean sendMail(TemplatesEnum templateId, Email email) {
        try {
            final String content = getTemplate(templateId, email);
            
            if (active) {
                MimeMessagePreparator preparator = (MimeMessage mimeMessage) -> {
                    mimeMessage.setSubject(templateId.getTitle());
                    mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
                    mimeMessage.setFrom(new InternetAddress(from));
                    mimeMessage.setContent(content, "text/html; charset=utf-8");
                };
                
                try {
                    this.mailSender.send(preparator);
                    return true;
                } catch (MailException ex) {
                    LOGGER.error("Error at send email", ex);
                    return false;
                }
            } else {
                LOGGER.info("Email to '{}' with message:\n{}", email.getTo(), content);
                return true;
            }
        } catch (ServiceException ex) {
            LOGGER.error("Error at send email", ex);
            return false;
        }
    }

    @GetMapping("/")
    private String getTemplate(TemplatesEnum templateEnum, Email email) throws ServiceException {
        email.setFrom(from);
        Map<String, Object> values = new HashMap<>();
        values.put("frontUrl", frontUrl);
        values.put("frontImageUrl", frontImageUrl);
        String url = "";
        try {
            Template template = freemarkerConfiguration.getTemplate(templateEnum.value(), Constants.ENCODING); //Habria que ver la optimizacion, solo se debe pedir una vez
            switch (templateEnum) {
                case RECOVERY_PASSWORD_EMAIL:
                    url = String.format("%s%s%s", frontUrl, Constants.RECOVERY_PASSWORD_URL, email.getMessage());
                    values.put("name", email.getContent().get(Constants.GENERAL_PARAM_NAME));
                    values.put("recoveryLink", url);

                    break;
                case CREATE_USER_BY_ADMIN:
                    url = String.format("%s%s%s", frontUrl, Constants.RECOVERY_PASSWORD_URL, email.getMessage());
                    values.put("name", email.getContent().get(Constants.GENERAL_PARAM_NAME));
                    values.put("recoveryLink", url);

                    break;
                case DELETE_ACCOUNT_MAIL:
                    values.put("name", email.getContent().get(Constants.GENERAL_PARAM_NAME));
                    values.put("support_email", supportEmail);
                    
                    break;
                case CREATE_SIMPLE_ACCOUNT:
                    url = String.format("%s%s%s", frontUrl, Constants.VALIDATE_ACCOUNT_URL, email.getMessage());
                    values.put("recoveryUrl", url);
                    break;
                    
                case DELETE_SIMPLE_ACCOUNT:
                    values.put("name", email.getContent().get(Constants.GENERAL_PARAM_NAME));
                    break;
                default:
                    values.put("message", email.getMessage());
                    values.put("content", email.getContent());
            }
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, values);
        } catch (IOException | TemplateException e) {
            throw new ServiceException(e);
        }

    }

}
