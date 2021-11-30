package cz.muni.fi.pv217.lepsiedajmejedlo.payments;

import com.google.common.base.Throwables;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import reactor.core.publisher.Mono;

@Slf4j
@EnableTransactionManagement
@EnableReactiveMongoAuditing
@Configuration(proxyBeanMethods = false)
@PropertySource(value = "classpath:application.properties")
@EnableReactiveMongoRepositories(basePackages = {"cz.muni.fi.pv217.lepsiedajmejedlo.payments"})
public class PersistenceConfig extends AbstractReactiveMongoConfiguration {

    @Value("${spring.data.mongodb.uri:mongodb://localhost/test}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database:payment-service}")
    private String mongoDatabaseName;

    @Override
    public MongoClient reactiveMongoClient() {
        MongoClient mongoClient = MongoClients.create(mongoUri);

        try {
            Mono.from(mongoClient.listDatabaseNames()).block(); // block intentionally on startup
        } catch (RuntimeException mongoException) {
            log.error("Mongo client creation failed: [{}]",
                    Throwables.getRootCause(mongoException).getLocalizedMessage());
            log.warn("Starting in degraded state without Mongo connection, will try to reconnect on operation");
        }
        return mongoClient;
    }

    @Override
    protected String getDatabaseName() {
        return mongoDatabaseName;
    }

    @Bean
    @Primary
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    @Primary
    @DependsOn({"validator"})
    public ValidatingMongoEventListener validatingMongoEventListener(LocalValidatorFactoryBean validator) {
        return new ValidatingMongoEventListener(validator);
    }
}
