package com.salesianostriana.dam.DanielOlivaMiarma;

import com.salesianostriana.dam.DanielOlivaMiarma.config.StorageProperties;
import com.salesianostriana.dam.DanielOlivaMiarma.security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableConfigurationProperties({StorageProperties.class})
@SpringBootApplication
public class DanielOlivaMiarmaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanielOlivaMiarmaApplication.class, args);
	}

}
