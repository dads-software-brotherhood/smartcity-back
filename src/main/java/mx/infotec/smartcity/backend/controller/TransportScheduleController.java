package mx.infotec.smartcity.backend.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import mx.infotec.smartcity.backend.model.DayName;
import mx.infotec.smartcity.backend.model.Time;
import mx.infotec.smartcity.backend.model.transport.Agency;
import mx.infotec.smartcity.backend.model.transport.TransportSchedule;
import mx.infotec.smartcity.backend.model.transport.WeekDay;
import mx.infotec.smartcity.backend.persistence.AgencyRepository;
import mx.infotec.smartcity.backend.persistence.TransportScheduleRepository;
import mx.infotec.smartcity.backend.utils.ControllerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Infotec
 */
@RestController
@RequestMapping("/transport-schedule")
public class TransportScheduleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportScheduleController.class);

    @Autowired
    private TransportScheduleRepository transportScheduleRepository;
    @Autowired
    private AgencyRepository agencyRepository;
    
    @RequestMapping(method = RequestMethod.GET)
    public List<TransportSchedule> getAll(@RequestParam(value = "routeName", required = false) String routeName) {
        if (routeName == null) {
            return transportScheduleRepository.findAll();
        } else {
            return transportScheduleRepository.findByRouteName(routeName);
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/page/{page}/{size}")
    public Page<TransportSchedule> getPage(@PathVariable("page") Integer page, @PathVariable("size") Integer size,
            @RequestParam(value = "routeName", required = false) String routeName,
            @RequestParam(value = "frequencyHour", required = false) Integer frequencyHour,
            @RequestParam(value = "frequencyMinute", required = false) Integer frequencyMinute,
            @RequestParam(value = "idAgency", required = false) String idAgency) {
        Pageable pageable = new PageRequest(page, size);
        
        if (routeName == null && idAgency == null && frequencyHour == null && frequencyMinute == null) {
            return transportScheduleRepository.findAll(pageable);
        } else {
            TransportSchedule transportSchedule = new TransportSchedule();
            
            if (routeName != null) {
                transportSchedule.setRouteName(routeName);
            }
            if (frequencyHour != null || frequencyMinute != null) {
                Time frequency = new Time();
                
                frequency.setHour(frequencyHour);
                frequency.setMinute(frequencyMinute);
                
                if (frequencyHour != null && frequencyMinute == null) {
                    frequency.setMinute(0);
                }             
                
                transportSchedule.setFrequency(frequency);
            }
            if (idAgency != null) {
                Agency agency = new Agency();
                agency.setId(idAgency);
                
                transportSchedule.setAgency(agency);
            }
            
            ExampleMatcher matcher = ExampleMatcher.matchingAll()
                    .withMatcher("routeName", match -> match.contains().ignoreCase())
                    .withIgnorePaths("weekDays", "id", "creatorId")
                    .withIgnoreNullValues();
            
            Example<TransportSchedule> example = Example.of(transportSchedule, matcher);
            
            
            
            return transportScheduleRepository.findAll(example, pageable);
            
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        TransportSchedule tmp = transportScheduleRepository.findOne(id);
        
        if (tmp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(tmp);
        }
        
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/day/{day}")
    public ResponseEntity<?> getByDay(@PathVariable String day) {
        List<TransportSchedule> tmp = transportScheduleRepository.findByActiveDay(day);
        
        if (tmp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found");
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(tmp);
        }
        
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody TransportSchedule transportSchedule, HttpServletRequest request) {
        if (!isValid(transportSchedule)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Route name, all weekdays, frecuency and agency are required");
        } else if (transportSchedule.getId() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID must be null");
        } else {
            try {
                transportSchedule.setCreatorId(ControllerUtils.getUserId(request));
                return ResponseEntity.accepted().body(transportScheduleRepository.insert(transportSchedule));
            } catch (Exception ex) {
                LOGGER.error("Error at insert", ex);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<?> update(@RequestBody TransportSchedule transportSchedule, @PathVariable("id") String id, HttpServletRequest request) {
        if (!isValid(transportSchedule)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Route name, all weekdays, frecuency and agency are required");
        } else {
            TransportSchedule origin = transportScheduleRepository.findOne(id);

            if (!ControllerUtils.canDeleteUpdate(origin.getCreatorId(), request)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only SA user or owner data can update");
            } else {
                if (transportSchedule.getId() != null) {
                    LOGGER.warn("Id must be ignored");
                    transportSchedule.setId(id);
                }

                try {
                    transportSchedule.setCreatorId(origin.getCreatorId());
                    transportScheduleRepository.save(transportSchedule);
                    return ResponseEntity.accepted().body("updated");
                } catch (Exception ex) {
                    LOGGER.error("Error at update", ex);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
                }
            }
        }
    }
    
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request) {
        try {
            TransportSchedule transportSchedule = transportScheduleRepository.findOne(id);
            
            if (transportSchedule != null && ControllerUtils.canDeleteUpdate(transportSchedule.getCreatorId(), request)) {
                transportScheduleRepository.delete(id);
                return ResponseEntity.accepted().body("deleted");          
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only SA user or owner data can delete");
            }
        } catch (Exception ex) {
            LOGGER.error("Error at delete", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
    
    private boolean isValid(TransportSchedule transportSchedule) {
        if (transportSchedule != null) {
            if (transportSchedule.getRouteName() != null && isValid(transportSchedule.getAgency())
                    && isValid(transportSchedule.getFrequency())
                    && transportSchedule.getWeekDays() != null && transportSchedule.getWeekDays().size() == 7) {

                Set<DayName> dayNames = new HashSet<>();

                for (WeekDay weekDay : transportSchedule.getWeekDays()) {
                    dayNames.add(weekDay.getDayName());

                    if (weekDay.isActive() && !isValid(weekDay.getArrivalTime()) && !isValid(weekDay.getDepartureTime())) {
                        return false;
                    }
                }

                return dayNames.size() == 7; //The seven days in enum
            }
        }

        return false;
    }

    private boolean isValid(Time time) {
        return time != null && (time.getHour() != null || time.getMinute() != null); //TODO: compeltar
    }

    private boolean isValid(Agency agency) {
        return agency != null && agency.getId() != null && agencyRepository.findOne(agency.getId()) != null;
    }

}
