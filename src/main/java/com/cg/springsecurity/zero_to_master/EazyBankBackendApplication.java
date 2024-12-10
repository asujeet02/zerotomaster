package com.cg.springsecurity.zero_to_master;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@EnableJpaRepositories("com.cg.springsecurity.zero_to_master.repository")
@EntityScan("com.cg.springsecurity.zero_to_master.model")*/
public class EazyBankBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EazyBankBackendApplication.class, args);
	}

}
