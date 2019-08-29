package io.iochord.dev.chdsr.simulator.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @package chdsr-simulator-web
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since 2019
 *
 *
 */
@SpringBootApplication
@EnableAutoConfiguration
//	(exclude = { DataSourceAutoConfiguration.class,
//		DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class, MongoAutoConfiguration.class })
@ComponentScan("io.iochord.dev.chdsr.simulator.web")
@Configuration
public class ChdsrWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChdsrWebApplication.class);
	}

}
