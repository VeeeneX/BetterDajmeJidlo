package cz.muni.fi.pv217.lepsiedajmejedlo.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * This class is entry point of Spring Boot application.
 * Its place where configuration starts during application
 * initialization. It registers all modules functionality,
 * provided as dependencies, by scanning for package
 * prefix <i><b>cz.servermechanics.keys.api</b></i>.
 *
 * @author norbert.dopjera@servermechanics.cz
 */
@SpringBootApplication
public class PaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsApplication.class, args);
	}

}
