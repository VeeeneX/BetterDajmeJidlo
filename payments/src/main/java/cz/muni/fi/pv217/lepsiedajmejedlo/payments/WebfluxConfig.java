package cz.muni.fi.pv217.lepsiedajmejedlo.payments;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * This WebFlux Java configuration declares the components that
 * are required to process requests with annotated controllers
 * or functional endpoints, and it offers an API to customize
 * the configuration.
 *
 * @author norbert.dopjera@servermechanics.cz
 */
@Configuration
@EnableWebFlux
public class WebfluxConfig implements WebFluxConfigurer {
}
