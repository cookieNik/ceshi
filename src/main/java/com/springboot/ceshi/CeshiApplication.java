package com.springboot.ceshi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.springboot.ceshi.mapper")
public class CeshiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CeshiApplication.class, args);
	}

}
