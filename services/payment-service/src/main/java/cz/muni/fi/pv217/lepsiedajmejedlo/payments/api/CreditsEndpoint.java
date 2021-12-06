package cz.muni.fi.pv217.lepsiedajmejedlo.payments.api;

import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.UserCredits;
import cz.muni.fi.pv217.lepsiedajmejedlo.payments.service.CreditsService;
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
import java.util.Map;

@Slf4j
@RestController
@PropertySource(value = "classpath:application.properties")
@RequestMapping(value = "/payments/v1/credits",
                produces = MediaType.APPLICATION_JSON_VALUE)
public class CreditsEndpoint {

    final CreditsService creditsService;

    public CreditsEndpoint(CreditsService creditsService) {
        this.creditsService = creditsService;
    }

    @GetMapping("/{userId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema))})
    public Mono<ResponseEntity<UserCredits>> getCredits(@PathVariable String userId) {
        return creditsService.getCredits(userId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK"))
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<UserCredits> getAll() {
        return creditsService.getAllCredits();
    }

    @PutMapping("/")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema))})
    public Mono<ResponseEntity<UserCredits>> updateCredits(@Valid @RequestBody UserCredits userCredits) {
        return creditsService.updateCredits(userCredits)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(path = "/{userId}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema))})
    public Mono<ResponseEntity<Map<String, String>>> deleteAccount(@PathVariable String userId) {
        return creditsService.deleteCredits(userId)
                .map(id -> ResponseEntity.ok().body(Map.of("id", id)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
