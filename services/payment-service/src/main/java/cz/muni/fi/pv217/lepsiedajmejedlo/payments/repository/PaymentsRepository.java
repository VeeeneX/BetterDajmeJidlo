package cz.muni.fi.pv217.lepsiedajmejedlo.payments.repository;

import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.Payment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PaymentsRepository extends ReactiveCrudRepository<Payment, String> {
    Flux<Payment> findAllByUserId(@NonNull String userId);
}
