package com.team.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.generator.dao")
@ComponentScan(basePackages = {"com.generator.controller"})
@ComponentScan(basePackages = {"com.generator.service"})
@ComponentScan(basePackages = {"com.team.demo.common"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
