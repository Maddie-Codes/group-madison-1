package com.launchcode.violetSwap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.launchcode.violetSwap")
public class VioletSwapApplication {

	public static void main(String[] args) {
		SpringApplication.run(VioletSwapApplication.class, args);
	}

}
