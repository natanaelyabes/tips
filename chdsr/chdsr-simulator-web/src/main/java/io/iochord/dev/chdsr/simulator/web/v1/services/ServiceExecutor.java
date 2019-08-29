package io.iochord.dev.chdsr.simulator.web.v1.services;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ServiceExecutor {
	
	public static final String NAME = "service";

	@Bean(name = NAME)
	public Executor getExecutor() {
		ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
		ex.setCorePoolSize(4);
		ex.setMaxPoolSize(4);
		ex.setQueueCapacity(4);
		return ex;
	}

}
