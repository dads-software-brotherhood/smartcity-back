package mx.infotec.smartcity.backend.controller.security;

import mx.infotec.smartcity.backend.model.TokenRequest;
import mx.infotec.smartcity.backend.service.exception.ServiceException;
import mx.infotec.smartcity.backend.service.recovery.TokenRecoveryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Service used for recover password operations.
 * 
 * @author Infotec
 */
public class RecoverPasswordController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RecoverPasswordController.class);
    
    @Autowired
    TokenRecoveryService recoveryService;

    /**
     * Method used to start password recovery.
     * 
     * @param email User email
     */
    @RequestMapping(method = RequestMethod.POST, value = "/forgot-password/{email}")
    public ResponseEntity<?> forgotPassword(String email) {
        //TODO: Debe crear un "token" en la entidad de Token (el token es en realidad el ID),
        // en la entidad debe guardarse el email del solicitante y su id de usaurio (del IDM)
        // asi como la fecha en que se creo.
        //
        // Al finalizar deberá enviar un correo al usuario para que este complete la recuperacion.
        // la URL del endpoint para el usuario debe ser http://<ip o url del front>/restore-password/{token}
        // donde {token} es el ID del token generado.
        //
        // Debe validar que el correo existe en la tabla de usuarios y agregar a la bitacora en caso de que no exista
      try {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(recoveryService.recoveryPassword(email));
      } catch (ServiceException e) {
        LOGGER.error("forgotPassword error, cause: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
      }
    }
    
    /**
     * 
     * 
     * @param recoveryToken
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET, value = "/forgot-password")
    public ResponseEntity<?> validToken(@RequestHeader(value = "recovery-token") String recoveryToken) {
        //TODO: Debe revisar que el token que me enviaron existe en la entidad Token (busqueda por ID),
        // de existir, deberá validar la fecha no mayor a 24 horas (o en caso de definiarlo como un parámetro, el tiempo máximo permitido);
        // de ser valido, regresar un codigo de aceptado en otro caso un codigo de error como el de no autorizado.
        
        try {
          if (recoveryService.validateTokenRecovery(recoveryToken)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("success");
          } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");
          }
        } catch (ServiceException e) {
          LOGGER.error("validToken error, cause: ", e);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
        }
    }
    
    /**
     * 
     * @param recoveryToken
     * @param tokenRequest
     * @return 
     */
    @RequestMapping(method = RequestMethod.POST, value = "/forgot-password", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> restorePassword(@RequestHeader(value = "recovery-token") String recoveryToken, @RequestBody TokenRequest tokenRequest) {
        //TODO: Debe validar el token (fecha y que exista), en el caso deberá actualizar la contraseña del usuario con la proporcionada por el toeknRequest
        // en caso de que todo sea correcto, se deberá eliminar el token en la entidad Token y enviar un correo de aceptación.
        // en caso contrario, se deberá enviar un codigo de error
        
        throw new UnsupportedOperationException();
    }
    
}
