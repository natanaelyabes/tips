package io.iochord.apps.ips.simulator.web.v1.api.controllers;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.apps.ips.core.services.AnIpsService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.core.services.ServiceContext.State;
import io.iochord.apps.ips.core.services.ServiceExecutor;
import io.iochord.apps.ips.simulator.web.v1.controllers.AController;
import lombok.Getter;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public abstract class AServiceController extends AController {
	public static final String BASE_URI = AController.BASE_URI + "/api/v1";
	
	@Autowired
	@Getter
	@JsonIgnore
	private DataSource dataSource;

	@Autowired
	@Getter
	@JsonIgnore
	private SimpMessagingTemplate wsmTemplate; 

	@Autowired
	@Getter
	private ServiceExecutor executor;

	public ServiceContext getServiceContext() {
		ServiceContext context =  new ServiceContext();
		context.setDataSource(getDataSource());
		context.setWsmTemplate(getWsmTemplate());
		return context;
	}
	
	public ServiceContext getServiceContext(Object data) {
		ServiceContext context = getServiceContext();
		context.setData(data);
		context.getInfo().setState(State.COMPLETED);
		return context;
	}
	
	public <C, R> ServiceContext run(AnIpsService<C, R> service, String jsonConfig, Class<C> configClass, Class<R> resultClass, HttpHeaders headers) {
		ServiceContext context = getServiceContext();
		if (context != null) {
			boolean runAsync = headers.containsKey("X-IOCHORD-WSA") && service.isAsync();
			if (runAsync) {
				getExecutor().runAsync(context, service, jsonConfig, configClass, resultClass);
			} else {
				context = getExecutor().run(context, service, jsonConfig, configClass, resultClass);
			}
		}
		return context;
	}
	
	public <C, R> ServiceContext run(AnIpsService<C, R> service, C config, Class<R> resultClass, HttpHeaders headers) {
		ServiceContext context = getServiceContext();
		if (context != null) {
			boolean runAsync = headers.containsKey("X-IOCHORD-WSA") && service.isAsync();
			if (runAsync) {
				getExecutor().runAsync(context, service, config, resultClass);
			} else {
				context = getExecutor().run(context, service, config, resultClass);
			}
		}
		return context;
	}

}
