package com.haven.graguation.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.haven.graguation.project.repository.mybatis.repository")
public class GraguationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraguationProjectApplication.class, args);
	}
}
