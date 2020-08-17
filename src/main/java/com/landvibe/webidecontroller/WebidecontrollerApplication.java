package com.landvibe.webidecontroller;

import com.landvibe.webidecontroller.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WebidecontrollerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebidecontrollerApplication.class, args);
	}

}
