package mx.infotec.smartcity.backend.persistence;



import java.util.List;

import mx.infotec.smartcity.backend.model.DayName;
import mx.infotec.smartcity.backend.model.Time;
import mx.infotec.smartcity.backend.model.transport.TransportSchedule;
import mx.infotec.smartcity.backend.model.transport.WeekDay;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Erik Valdivieso
 */
public interface TransportScheduleRepository extends MongoRepository<TransportSchedule, String> {

    @Query(value = "{'routeName': {$regex : ?0}}")
    List<TransportSchedule> findByRouteName(String routeName);
    
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': ?0}}}")
    List<TransportSchedule> findByActiveDayQuery(String routeName);
    
    
    
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': ?0}}}")
    Page<List<TransportSchedule>> findByWeekDaysAndWeekDaysDepartureTime(DayName dayname,boolean value,Time time,Pageable pageable);
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': ?0}}}")
    Page<List<TransportSchedule>> findByWeekDaysDepartureTime(Time time,Pageable pageable);
    
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': ?0}}}")
    Page<List<TransportSchedule>> findByWeekDays(List<WeekDay> weekDay,Pageable pageable);
 
    
    Page<List<TransportSchedule>> findByWeekDaysLike(List<WeekDay> weekDay,Pageable pageable);
   
    @Query(value = "{ 'weekDays': { '$elemMatch':{ 'active': true, 'dayName': {$in:?0} }}}")
    List<TransportSchedule> findByActiveDaysQuery(List<String> dayname);
     
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': {$in:?0} ,'departureTime':?1}}}")
    List<TransportSchedule> findByActiveDaysAndDepartureTimeQuery(List<String> dayname,Time time);
    
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': {$in:?0} , 'arrivalTime':?1}}}")
    List<TransportSchedule> findByActiveDaysArrivalTimeQuery(List<String> dayname,Time time);
   
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': {$in:?0} ,'departureTime':?1, 'arrivalTime':?2}}}")
    List<TransportSchedule> findByActiveDaysAndDepartureTimeAndArrivalTimeQuery(List<String> dayname,Time time,Time time2);
    
   
    List<TransportSchedule> findByWeekDaysDepartureTimeAndWeekDaysArrivalTime(Time time, Time time2);
    
   
    List<TransportSchedule> findByWeekDaysArrivalTime(Time time);
    
    List<TransportSchedule> findByWeekDaysDepartureTime(Time time);
    
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': {$in:?0} }}, 'routeName': ?1}")
    List<TransportSchedule> findByActiveDaysAndRouteNameQuery(List<String> dayname, String routeName);
     
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': {$in:?0} ,'departureTime':?1}}, 'routeName': ?2}")
   List<TransportSchedule> findByActiveDaysAndDepartureTimeRouteNameQuery(List<String> dayname,Time time, String routeName);
    
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': {$in:?0} , 'arrivalTime':?1}}, 'routeName': ?2}")
    List<TransportSchedule> findByActiveDaysArrivalTimeRouteNameQuery(List<String> dayname,Time time, String routeName);
   
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'dayName': {$in:?0} ,'departureTime':?1, 'arrivalTime':?2}}, 'routeName': ?3}")
    List<TransportSchedule> findByActiveDaysAndDepartureTimeAndArrivalTimeRouteNameQuery(List<String> dayname,Time time,Time time2, String routeName);
    
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true,'departureTime':?0, 'arrivalTime':?1}}, 'routeName': ?2}")
   List<TransportSchedule> findByDepartureTimeAndArrivalTimeRouteNameQuery(Time time,Time time2, String routeName);
    
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true, 'arrivalTime':?0}}, 'routeName': ?1}")
   List<TransportSchedule> findByArrivalTimeAndRouteNameQuery(Time time, String routeName);
    
    @Query(value = "{ 'weekDays': { '$elemMatch': { 'active': true,'departureTime':?0 }}, 'routeName': ?1}")
    List<TransportSchedule> findByDepartureTimeAndRouteNameQuery(Time time, String routeName);
    
   
    
    
}
