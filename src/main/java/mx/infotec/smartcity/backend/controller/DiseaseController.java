package mx.infotec.smartcity.backend.controller;

import java.util.List;
import mx.infotec.smartcity.backend.model.Disease;
import mx.infotec.smartcity.backend.persistence.DiseaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author adrian
 */
@RestController
@RequestMapping("/diseases")
public class DiseaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiseaseController.class);

    @Autowired
    private DiseaseRepository diseaseRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/count")
    public long count() {
        return diseaseRepository.count();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Disease> getAll(@RequestHeader HttpHeaders headers) {
        LOGGER.debug("Headers: {}", headers);

        return diseaseRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Disease getById(@PathVariable String id) {
        return diseaseRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteByID(@PathVariable String id) {
        try {
            diseaseRepository.delete(id);
            return ResponseEntity.accepted().body(null);
        } catch (Exception ex) {
            LOGGER.error("Error at delete", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Disease disease) {
        if (disease.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID must be null");
        } else {
            try {
                return ResponseEntity.accepted().body(diseaseRepository.insert(disease));
            } catch (Exception ex) {
                LOGGER.error("Error at insert", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> update(@RequestBody Disease disease, @PathVariable("id") String id) {
        try {
            if (diseaseRepository.exists(id)) {
                if (disease.getId() != null) {
                    LOGGER.warn("ID from object is ignored");
                }

                disease.setId(id);
                diseaseRepository.save(disease);
                
                return ResponseEntity.accepted().body("updated");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID don't exists");
            }
        } catch (Exception ex) {
            LOGGER.error("Error at update", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }

    }
}
