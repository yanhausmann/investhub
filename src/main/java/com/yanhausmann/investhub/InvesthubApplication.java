package com.yanhausmann.investhub;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableFeignClients
public class InvesthubApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvesthubApplication.class, args);
	}

}
