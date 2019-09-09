package io.iochord.apps.ips.simulator.web.v1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.iochord.apps.ips.data.services.DataConnectionService;
import io.iochord.apps.ips.model.analysis.services.IsmDiscoveryService;
import io.iochord.apps.ips.services.ServiceState;
import io.iochord.apps.ips.simulator.web.v1.models.WebServiceResponse;
import lombok.Getter;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@Service
public class AllServices {
	
	public String getServiceSession() {
		String session = String.valueOf(System.nanoTime());
		return session;
	}

	public <T> WebServiceResponse<T> createResponse(Class<T> clazz) {
		return createResponse(null, clazz);
	}

	public <T> WebServiceResponse<T> createResponse(T data, Class<T> clazz) {
		String session = getServiceSession();
		ServiceState state = new ServiceState(session);
		state.setProgressWsUri("/response/progress/" + session);
		state.setCompleteWsUri("/response/completed/" + session);
		WebServiceResponse<T> response = new WebServiceResponse<T>(data);
		response.setState(state);
		return response;
	}
	
	public <T> WebServiceResponse<T> createCompletedResponse(Class<T> clazz) {
		return createCompletedResponse(null, clazz);
	}
	
	public <T> WebServiceResponse<T> createCompletedResponse(T data, Class<T> clazz) {
		WebServiceResponse<T> response = createResponse(data, clazz);
		response.getState().setStatus(ServiceState.STATE.COMPLETED);
		return response;
	}
	
	@Autowired
	@Getter
	private DataConnectionService dataConnectionService;
	
	@Autowired
	@Getter
	private IsmDiscoveryService modelIsmDiscoveryService;
	
	
	
}
