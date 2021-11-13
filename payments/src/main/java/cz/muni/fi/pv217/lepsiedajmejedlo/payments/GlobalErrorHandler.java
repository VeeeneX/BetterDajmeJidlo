package cz.muni.fi.pv217.lepsiedajmejedlo.payments;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Order(0)
@Configuration
public class GlobalErrorHandler implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(error -> (error instanceof ResponseStatusException)
                        ? Mono.error(error)
                        : handle(exchange, error)
                );
    }

    public Mono<Void> handle(ServerWebExchange exchange, Throwable error) {
        log.error("request_id={} origin={} - {}",
                exchange.getRequest().getId(), error.getClass().getName(), error.getLocalizedMessage());
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        DataBuffer dataBuffer = bufferFactory.wrap("Unknown error".getBytes());
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }
}
