package cz.muni.fi.pv217.lepsiedajmejedlo.payments.service;

import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.UserCredits;
import cz.muni.fi.pv217.lepsiedajmejedlo.payments.repository.CreditsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CreditsDatabaseService implements CreditsService {

    final CreditsRepository creditsRepository;

    public CreditsDatabaseService(CreditsRepository creditsRepository) {
        this.creditsRepository = creditsRepository;
    }

    @Override
    public Mono<UserCredits> getCredits(@NonNull String userId) {
        return creditsRepository.findByUserId(userId);
    }

    @Override
    public Flux<UserCredits> getAllCredits() {
        return creditsRepository.findAll();
    }

    @Override
    public Mono<UserCredits> updateCredits(@NonNull UserCredits userCredits) {
        return creditsRepository.save(userCredits);
    }

    @Override
    public Mono<String> deleteCredits(@NonNull String userId) {
        return creditsRepository.findByUserId(userId)
                .flatMap(userCredits -> creditsRepository
                        .deleteByUserId(userCredits.getUserId())
                        .then(Mono.just(userId))
                );
    }
}
