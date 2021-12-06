package cz.muni.fi.pv217.lepsiedajmejedlo.payments.service;

import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.UserCredits;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CreditsService {
    Mono<UserCredits> getCredits(@NonNull String userId);
    Flux<UserCredits> getAllCredits();
    Mono<UserCredits> updateCredits(@NonNull UserCredits userCredits);
    Mono<String> deleteCredits(@NonNull String userId);
}
