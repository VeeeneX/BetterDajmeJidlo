package cz.muni.fi.pv217.lepsiedajmejedlo.payments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.value;

@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final long startTime = System.currentTimeMillis();
        return chain.filter(exchange)
                .doOnSuccess(aVoid -> logResponse(exchange, startTime,
                        Optional.ofNullable(exchange.getResponse().getStatusCode())
                                .orElse(HttpStatus.INTERNAL_SERVER_ERROR).value())
                ).doOnError(error -> logResponse(exchange, startTime,
                        (error instanceof ResponseStatusException)
                                ? ((ResponseStatusException) error).getStatus().value()
                                : HttpStatus.INTERNAL_SERVER_ERROR.value())
                );
    }

    private void logResponse(final ServerWebExchange exchange, long startTime, int overriddenStatus) {
        final long duration = System.currentTimeMillis() - startTime;
        log.info("{} uri={} status={} request_id={} audit={} - {} ms",
                exchange.getRequest().getMethod(), exchange.getRequest().getPath(),
                overriddenStatus, exchange.getRequest().getId(),
                value("audit", true), duration);
    }
}
