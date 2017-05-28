package mx.infotec.smartcity.backend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import mx.infotec.smartcity.backend.model.IdentityUser;
import mx.infotec.smartcity.backend.model.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.smartcity.backend.model.transport.ProfilePublicTransport;
import mx.infotec.smartcity.backend.model.transport.PublicTransport;
import mx.infotec.smartcity.backend.persistence.ProfilePublicTransportRepository;
import mx.infotec.smartcity.backend.persistence.PublicTransportRepository;
import mx.infotec.smartcity.backend.utils.Constants;
import mx.infotec.smartcity.backend.utils.ControllerUtils;

/**
 * RestService Public Transport.
 *
 * @author Benjamin Vander Stichelen
 */
@RestController
@RequestMapping("/public-transport")
public class PublicTransportController {

  private static final Logger              LOGGER    =
      LoggerFactory.getLogger(PublicTransportController.class);

  @Autowired
  private PublicTransportRepository        publicTransportRepository;


  @Autowired
  private ProfilePublicTransportRepository profilePublicTransportRepository;

  private int                              SIZE      = 5;
  private String                           PROFILEID = "ProfileId";

  @RequestMapping(method = RequestMethod.GET)
  public List<PublicTransport> getByAll() {
    return publicTransportRepository.findAll();
  }


  @RequestMapping(method = RequestMethod.GET, value = "/{id}")
  public PublicTransport getById(@PathVariable("id") String id) {
    return publicTransportRepository.findOne(id);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/page/{page}/{size}")
  public Page<PublicTransport> getByPageSize(@PathVariable("page") String page,
      @PathVariable("size") String size) {
    Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size));

    return publicTransportRepository.findAll(pageable);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/page/{page}")
  public Page<PublicTransport> getByPage(@PathVariable("page") int page) {
    Pageable pageable = new PageRequest(page, SIZE);
    return publicTransportRepository.findAll(pageable);
  }

  @RequestMapping(method = RequestMethod.GET, value = "/profile/{id}")
  public ResponseEntity<?> getPublicTransportWithProfileId(@PathVariable("id") String id) {
    try {
      return ResponseEntity.accepted()
          .body(this.publicTransportRepository.findAll(getListaIdPublicTransportIterator(id)));
      // return ResponseEntity.accepted().body(getListaPublicTransport(id));

    } catch (Exception ex) {
      LOGGER.error("Error at update", ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }



  @RequestMapping(method = RequestMethod.GET, value = "/profile/{id}/page/{page}")
  public ResponseEntity<?> getPublicTransportWithProfileId(@PathVariable("id") String id,
      @PathVariable("page") int page) {
    try {
      Pageable pageable = new PageRequest(page, SIZE);

      Iterable<PublicTransport> ipt =
          this.publicTransportRepository.findAll(getListaIdPublicTransportIterator(id));
      List<PublicTransport> lpt = new ArrayList<PublicTransport>();
      ipt.forEach(lpt::add);
      return ResponseEntity.accepted()
          .body(new PageImpl<PublicTransport>(lpt, pageable, lpt.size()));
    } catch (Exception ex) {
      LOGGER.error("Error at update", ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }

  @RequestMapping(method = RequestMethod.GET, value = "/profile/{id}/page/{page}/size/{size}")
  public ResponseEntity<?> getPublicTransportWithProfileId(@PathVariable("id") String id,
      @PathVariable("page") int page, @PathVariable("size") int size) {
    try {
      Pageable pageable = new PageRequest(page, size);

      Iterable<PublicTransport> ipt =
          this.publicTransportRepository.findAll(getListaIdPublicTransportIterator(id));
      List<PublicTransport> lpt = new ArrayList<PublicTransport>();
      ipt.forEach(lpt::add);
      return ResponseEntity.accepted()
          .body(new PageImpl<PublicTransport>(lpt, pageable, lpt.size()));
    } catch (Exception ex) {
      LOGGER.error("Error at update", ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }

  private Iterable<String> getListaIdPublicTransportIterator(String idProfile) {
    Iterable<String> iterable =
        getListaIdPublicTransport(this.profilePublicTransportRepository.findByIdProfile(idProfile));


    return iterable;

  }

  private List<String> getListaIdPublicTransport(List<ProfilePublicTransport> lppt) {
    List<String> idsPublicTransport = new ArrayList<String>();
    for (ProfilePublicTransport ppt : lppt) {
      idsPublicTransport.add(ppt.getIdPublicTransport());
    }
    return idsPublicTransport;
  }

  private List<PublicTransport> getListaPublicTransport(String id) {
    List<PublicTransport> lPublicTransport = new ArrayList<PublicTransport>();
    for (String idTrasportePublico : getListaIdPublicTransport(
        this.profilePublicTransportRepository.findByIdProfile(id))) {
      addingToPublicTransportList(lPublicTransport, idTrasportePublico);
    }
    return lPublicTransport;
  }


  private void addingToPublicTransportList(List<PublicTransport> lPublicTransport,
      String idTrasportePublico) {
    PublicTransport pt = this.publicTransportRepository.findById(idTrasportePublico);
    if (pt != null) {
      lPublicTransport.add(pt);
    }
  }

  private List<PublicTransport> getListaPublicTransport(String id, Pageable pageable) {
    List<PublicTransport> lPublicTransport = new ArrayList<PublicTransport>();
    for (String idTrasportePublico : getListaIdPublicTransport(
        this.profilePublicTransportRepository.findByIdProfile(id, pageable))) {
      addingToPublicTransportList(lPublicTransport, idTrasportePublico);
    }

    return lPublicTransport;
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  public ResponseEntity<?> deleteByID(@PathVariable String id, HttpServletRequest request) {
    try {
      PublicTransport publicTransport = publicTransportRepository.findById(id);
      
      if (publicTransport != null && ControllerUtils.canDeleteUpdate(publicTransport.getCreatorId(), request)) {
          ProfilePublicTransport profilePublicTransport =
              profilePublicTransportRepository.findByIdPublicTransport(id);
          publicTransportRepository.delete(id);
          profilePublicTransportRepository.delete(profilePublicTransport);
          return ResponseEntity.accepted().body("deleted");          
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only SA user or owner data can delete");
      }
    } catch (Exception ex) {
      LOGGER.error("Error at delete", ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<?> add(@RequestBody PublicTransport publicTransport, HttpServletRequest request) {
    if (publicTransport.getId() != null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID must be null");
    } else if (!isValid(publicTransport)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name, BrandName and ModelName are required");
    } else {
      try {
        publicTransport.setCreatorId(ControllerUtils.getUserId(request));
        publicTransport.setDateCreated(new Date());
        
        PublicTransport publicTransportRepro = publicTransportRepository.insert(publicTransport);
        this.profilePublicTransportRepository
            .insert(new ProfilePublicTransport(this.PROFILEID, publicTransport.getId()));
        return ResponseEntity.accepted().body(publicTransportRepro);
      } catch (Exception ex) {
        LOGGER.error("Error at insert", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
      }
    }
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
      value = "/{id}")
  public ResponseEntity<?> update(@RequestBody PublicTransport publicTransport,
      @PathVariable("id") String id, HttpServletRequest request) {
    try {
      PublicTransport publicTransportOrin = publicTransportRepository.findById(id);
      
      if (publicTransportOrin == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID don't exists");
      } else if (!isValid(publicTransport)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name, BrandName and ModelName are required");
      } else if (!ControllerUtils.canDeleteUpdate(publicTransportOrin.getCreatorId(), request)) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only SA user or owner data can update");
      } else {
        if (publicTransport.getId() != null) {
          LOGGER.warn("ID from object is ignored");
        }

        publicTransport.setId(id);
        publicTransport.setDateCreated(publicTransportOrin.getDateCreated());
        publicTransport.setDateModified(new Date());
        publicTransport.setCreatorId(publicTransportOrin.getCreatorId());
        
        publicTransportRepository.save(publicTransport);

        return ResponseEntity.accepted().body("updated");
      }
    } catch (Exception ex) {
      LOGGER.error("Error at update", ex);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
  }

  private boolean isValid(PublicTransport publicTransport) {
    return publicTransport != null && publicTransport.getName() != null && publicTransport.getBrandName() != null && publicTransport.getModelName() != null && publicTransport.getPublicTransportFuelType() != null && publicTransport.getPublicTransportFuelType().getId() != null;
  }

}
