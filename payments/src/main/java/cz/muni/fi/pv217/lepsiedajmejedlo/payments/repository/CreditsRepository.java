package cz.muni.fi.pv217.lepsiedajmejedlo.payments.repository;

import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.UserCredits;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CreditsRepository extends ReactiveCrudRepository<UserCredits, String> {
    Mono<UserCredits> findByUserId(@NonNull String userId);
    Mono<Void> deleteByUserId(@NonNull String userId);
}
