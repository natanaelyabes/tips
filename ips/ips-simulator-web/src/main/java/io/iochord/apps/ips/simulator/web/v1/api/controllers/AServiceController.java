package io.iochord.apps.ips.simulator.web.v1.api.controllers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import io.iochord.apps.ips.services.ServiceState;
import io.iochord.apps.ips.simulator.web.v1.controllers.AController;
import io.iochord.apps.ips.simulator.web.v1.models.WebServiceResponse;
import io.iochord.apps.ips.simulator.web.v1.services.AllServices;
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
	private AllServices services;
	
	@Autowired
	@Getter
	private SimpMessagingTemplate wsmTemplate; 
	
	public <T> WebServiceResponse<T> printResult(HttpHeaders headers, Future<T> future, WebServiceResponse<T> response) throws InterruptedException, ExecutionException {
		if (!headers.containsKey("X-IOCHORD-WSA")) {
			response.setData(future.get());
			response.getState().setStatus(ServiceState.STATE.COMPLETED);
		}
		return response;
	}

}
