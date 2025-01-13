package fontys.sem6.circline.authentication;

import fontys.sem6.circline.authentication.persistence.AccountRepository;
import fontys.sem6.circline.authentication.persistence.entity.AccountEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class AuthenticationCirclineApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationCirclineApplication.class, args);
	}
//	@Bean
//	CommandLineRunner initDatabase(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
//	return args -> {
//
//
//		AccountEntity account3 = new AccountEntity();
//			account3.setUserId(Long.valueOf(896));
//			account3.setEmail("Sally@gmail.com");
//			account3.setPassword(passwordEncoder.encode("Ab@123"));
//			account3.setRole("USER");
//			accountRepository.save(account3);
//
//
//		};
//	}


}
