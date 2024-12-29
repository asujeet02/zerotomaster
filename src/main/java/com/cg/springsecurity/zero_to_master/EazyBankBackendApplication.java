package com.cg.springsecurity.zero_to_master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
/*@EnableJpaRepositories("com.cg.springsecurity.zero_to_master.repository")
@EntityScan("com.cg.springsecurity.zero_to_master.model")*/
@EnableWebSecurity(debug = true)
public class EazyBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazyBankBackendApplication.class, args);
	}

}
