package mx.infotec.smartcity.backend.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import mx.infotec.smartcity.backend.model.transport.ProfilePublicTransport;

/**
 *
 * @author Benjamin Vander Stichelen
 */
public interface ProfilePublicTransportRepository
    extends MongoRepository<ProfilePublicTransport, String> {

  // Regresa la lista de profiles de transporte publico con el id de profile
  List<ProfilePublicTransport> findByIdProfile(String idProfile);

  ProfilePublicTransport findByIdPublicTransport(String idPublicTransport);

  List<ProfilePublicTransport> findByIdProfile(String id, Pageable pageable);



}
