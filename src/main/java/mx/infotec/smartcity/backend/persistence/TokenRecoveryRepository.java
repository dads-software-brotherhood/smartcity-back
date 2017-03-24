package mx.infotec.smartcity.backend.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import mx.infotec.smartcity.backend.model.TokenRecovery;

public interface TokenRecoveryRepository extends MongoRepository<TokenRecovery, String> {

  public TokenRecovery findById(final String id);
}
