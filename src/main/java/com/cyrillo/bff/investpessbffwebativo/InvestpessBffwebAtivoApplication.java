package com.cyrillo.bff.investpessbffwebativo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.cyrillo.bff.investpessbffwebativo.core.entidade"})
@EnableJpaRepositories(basePackages = {"com.cyrillo.bff.investpessbffwebativo.core.repositorio"})
@ComponentScan(basePackages = {"com.cyrillo.bff.investpessbffwebativo.core.usecase","com.cyrillo.bff.investpessbffwebativo.infra.entrypoint"})
@SpringBootApplication
public class InvestpessBffwebAtivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestpessBffwebAtivoApplication.class, args);
	}

}
