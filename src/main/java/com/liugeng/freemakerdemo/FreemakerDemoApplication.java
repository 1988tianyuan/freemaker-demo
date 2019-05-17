package com.liugeng.freemakerdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class FreemakerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(FreemakerDemoApplication.class, args);
	}

}
