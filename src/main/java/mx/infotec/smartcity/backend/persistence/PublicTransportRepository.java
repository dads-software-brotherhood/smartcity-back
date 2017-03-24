package mx.infotec.smartcity.backend.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import mx.infotec.smartcity.backend.model.transport.PublicTransport;


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


}
