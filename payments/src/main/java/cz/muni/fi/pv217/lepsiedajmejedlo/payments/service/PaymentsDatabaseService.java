package cz.muni.fi.pv217.lepsiedajmejedlo.payments.service;

import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.Payment;
import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.UserCredits;
import cz.muni.fi.pv217.lepsiedajmejedlo.payments.repository.PaymentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class PaymentsDatabaseService implements PaymentsService {

    private final CreditsService creditsService;
    private final PaymentsRepository paymentsRepository;

    public PaymentsDatabaseService(CreditsService creditsService, PaymentsRepository paymentsRepository) {
        this.creditsService = creditsService;
        this.paymentsRepository = paymentsRepository;
    }

    @Override
    public Mono<Payment> createPayment(@NonNull Payment payment) {
        return creditsService.getCredits(payment.getUserId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "User is not registered")))
                .flatMap(userCredits -> userCredits.getCreditCount() < payment.getCreditAmount()
                    ? Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "User has not enough credits"))
                    : creditsService.updateCredits(userCredits.toBuilder()
                        .withCreditCount(userCredits.getCreditCount() - payment.getCreditAmount())
                        .build()
                    ).flatMap(updated -> paymentsRepository.save(payment))
                );
    }

    @Override
    public Mono<Payment> getPayment(@NonNull String id) {
        return paymentsRepository.findById(id);
    }

    @Override
    public Flux<Payment> getAll() {
        return paymentsRepository.findAll();
    }

    @Override
    public Flux<Payment> getAllByUserId(@NonNull String userId) {
        return paymentsRepository.findAllByUserId(userId);
    }
}
