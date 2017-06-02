package mx.infotec.smartcity.backend.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import mx.infotec.smartcity.backend.model.transport.PublicTransport;
import mx.infotec.smartcity.backend.model.transport.TransportSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.Query;


/**
 *
 * @author Erik Valdivieso
 */
public interface PublicTransportRepository extends MongoRepository<PublicTransport, String> {

  List<PublicTransport> findAllById(List<String> listId, Pageable pageable);

  List<PublicTransport> findAllById(List<String> listId);

  PublicTransport findById(String id);

  List<PublicTransport> findAllById(Iterable<String> listaIdPublicTransportIterator,
      Pageable pageable);

  @Query(value = "{ 'transportSchedules':  { 'weekDays': { '$elemMatch':{ 'active': true, 'dayName': {$in:?0} }}}}")
  Page<List<PublicTransport>> findByActiveDaysQuery(List<String> days, Pageable pageable);
  
  @Query(value = "{ 'transportSchedules': [{ 'weekDays': { 'routeName': ?0}}]}")
   Page<List<PublicTransport>> findByRouteNameQuery(String routeName, Pageable pageable);
  Page<List<PublicTransport>> findByTransportSchedules(List<TransportSchedule>ts, Pageable pageable);
  
  @Query(value = "{ 'name':  {$regex :?0}}")
  Page<List<PublicTransport>> findByNameQuery(String routeName, Pageable pageable);
  
  Page<List<PublicTransport>> findByTransportSchedulesRouteName(String routeName, Pageable pageable);
  
  Page<List<PublicTransport>> findByTransportSchedulesIn(List<TransportSchedule>ts, Pageable pageable);
  List<PublicTransport> findByTransportSchedulesIn(List<TransportSchedule>ts);
  List<PublicTransport> findByTransportSchedulesLike(List<TransportSchedule>ts);
  List<PublicTransport> findByTransportSchedules(List<TransportSchedule>ts);
  Page<List<PublicTransport>> findByNameLikeAndTransportSchedulesIn(String name, List<TransportSchedule>ts, Pageable pageable);
  
  List<PublicTransport> findByNameLikeAndTransportSchedulesIn(String name, List<TransportSchedule>ts);
  
  List<PublicTransport> findByNameLike(String name);
  

}
