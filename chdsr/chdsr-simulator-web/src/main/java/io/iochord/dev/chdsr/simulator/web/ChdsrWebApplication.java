package io.iochord.dev.chdsr.simulator.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
public class ChdsrWebApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ChdsrWebApplication.class);
	}
	
}
