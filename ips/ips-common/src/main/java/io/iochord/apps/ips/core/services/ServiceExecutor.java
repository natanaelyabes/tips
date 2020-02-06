package io.iochord.apps.ips.core.services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.iochord.apps.ips.common.util.DataCodec;
import io.iochord.apps.ips.common.util.JsonDataCodec;
import io.iochord.apps.ips.core.services.ServiceContext.State;

/**
*
* @package ips-common
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@Configuration
@EnableAsync
public class ServiceExecutor {

	public static final String NAME = "ips-se";

	@Bean(name = NAME)
	public Executor getExecutor() {
		ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
		ex.setCorePoolSize(4);
		ex.setMaxPoolSize(4);
		ex.setQueueCapacity(4);
		return ex;
	}

	@Async(NAME)
	public <C, R> CompletableFuture<ServiceContext> runAsync(ServiceContext context, AnIpsService<C, R> service,
			String jsonConfig, Class<C> configClass, Class<R> resultClass) {
		return CompletableFuture.completedFuture(run(context, service, jsonConfig, configClass, resultClass));
	}

	public <C, R> ServiceContext run(ServiceContext context, AnIpsService<C, R> service, String jsonConfig,
			Class<C> configClass, Class<R> resultClass) {
		DataCodec<String> codec = new JsonDataCodec();
		C config = codec.decode(jsonConfig, configClass);
		return run(context, service, config, resultClass);
	}

	@Async(NAME)
	public <C, R> CompletableFuture<ServiceContext> runAsync(ServiceContext context, AnIpsService<C, R> service,
			C config, Class<R> resultClass) {
		return CompletableFuture.completedFuture(run(context, service, config, resultClass));
	}

	public <C, R> ServiceContext run(ServiceContext context, AnIpsService<C, R> service, C config,
			Class<R> resultClass) {
		try {
			context.start();
			R result = service.run(context, config);
			context.completeAndDestroy(State.COMPLETED, 0, result);
		} catch (Exception ex) {
			ex.printStackTrace();
			context.completeAndDestroy(State.ERROR, -1, ex);
		}
		return context;
	}

}
