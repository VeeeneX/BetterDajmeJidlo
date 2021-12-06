package cz.muni.fi.pv217.lepsiedajmejedlo.payments.api;

import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.Payment;
import cz.muni.fi.pv217.lepsiedajmejedlo.payments.service.PaymentsService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@PropertySource(value = "classpath:application.properties")
@RequestMapping(value = "/payments/v1",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentsEndpoint {

    private final PaymentsService paymentsService;

    public PaymentsEndpoint(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @PostMapping("/")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema))})
    public Mono<ResponseEntity<Payment>> createPayment(@Valid @RequestBody Payment payment) {
        return paymentsService.createPayment(payment)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema))})
    public Mono<ResponseEntity<Payment>> getPayment(@PathVariable String id) {
        return paymentsService.getPayment(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<Payment> getAll() {
        return paymentsService.getAll();
    }

    @GetMapping("/user/{userId}")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<Payment> getAllByUserId(@PathVariable String userId) {
        return paymentsService.getAllByUserId(userId);
    }
}
