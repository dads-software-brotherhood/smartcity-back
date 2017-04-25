package mx.infotec.smartcity.backend.controller;

import mx.infotec.smartcity.backend.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Erik Valdivieso
 */
@RestController
@RequestMapping("/test-mail")
public class TestMailController {
    
    @Autowired
    private MailService mailService;
    
    @RequestMapping(value = "/cancel-account", method = RequestMethod.GET)
    public void testCancelAccount() {
        
    }
    
    @RequestMapping(value = "/cancel-account-admin", method = RequestMethod.GET)
    public void testCancelAccountAdmin() {
        
    }
    
    @RequestMapping(value = "/create-user", method = RequestMethod.GET)
    public void testCreateUser() {
        
    }
    
    @RequestMapping(value = "/create-user-admin", method = RequestMethod.GET)
    public void testCreateUserAdmin() {
        
    }
    
    @RequestMapping(value = "/recovery-password", method = RequestMethod.GET)
    public void testRecoveryPassword() {
        
    }
}
