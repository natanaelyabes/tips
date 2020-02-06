package io.iochord.apps.ips.simulator.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * IPS Web Application Entrypoint 
 *
 * @package ips-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("io.iochord.apps.ips")
@Configuration
public class IpsWebApplication {

	/**
	 * Application entrypoint
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(IpsWebApplication.class);
	}

}
