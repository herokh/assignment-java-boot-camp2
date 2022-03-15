package com.javabootcamp.assessment2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Assessment2Application {

	@Autowired
	private SeederWrapper seederWrapper;

	@PostConstruct
	public void initialData() {
		seederWrapper.initDataMocks();
	}

	public static void main(String[] args) {
		SpringApplication.run(Assessment2Application.class, args);
	}

}
