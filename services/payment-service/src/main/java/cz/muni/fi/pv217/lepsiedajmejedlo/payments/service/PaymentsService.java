package cz.muni.fi.pv217.lepsiedajmejedlo.payments.service;

import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.Payment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface PaymentsService {
    Mono<Payment> createPayment(@NonNull Payment payment);
    Mono<Payment> getPayment(@NonNull String id);
    Flux<Payment> getAll();
    Flux<Payment> getAllByUserId(@NonNull String userId);
}
