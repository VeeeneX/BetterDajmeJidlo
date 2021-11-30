package cz.muni.fi.pv217.lepsiedajmejedlo.payments;

import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.Payment;
import cz.muni.fi.pv217.lepsiedajmejedlo.payments.model.UserCredits;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.mapping.event.ReactiveAfterConvertCallback;
import org.springframework.data.mongodb.core.mapping.event.ReactiveAfterSaveCallback;
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeConvertCallback;
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeSaveCallback;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import org.springframework.nativex.hint.AccessBits;
import org.springframework.nativex.hint.TypeHint;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;

/**
 * This class is entry point of Spring Boot application.
 * Its place where configuration starts during application
 * initialization. It registers all modules functionality,
 * provided as dependencies, by scanning for package
 * prefix <i><b>cz.servermechanics.keys.api</b></i>.
 *
 * @author norbert.dopjera@servermechanics.cz
 */
@TypeHint(types = { OptionalValidatorFactoryBean.class },
		  access = AccessBits.FULL_REFLECTION)
@TypeHint(types = {
			SimpleReactiveMongoRepository.class,
			ReactiveAfterConvertCallback.class,
			ReactiveAfterSaveCallback.class,
			ReactiveBeforeConvertCallback.class,
			ReactiveBeforeSaveCallback.class
		  },
		  access = AccessBits.FULL_REFLECTION)
@SpringBootApplication
public class PaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentsApplication.class, args);
	}

}
