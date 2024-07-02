package com.nur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransactionMgtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionMgtServiceApplication.class, args);
	}

}
