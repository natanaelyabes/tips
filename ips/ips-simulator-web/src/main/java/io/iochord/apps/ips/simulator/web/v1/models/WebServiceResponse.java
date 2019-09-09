package io.iochord.apps.ips.simulator.web.v1.models;

import io.iochord.apps.ips.services.ServiceState;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class WebServiceResponse<T> {

	@Getter
	@Setter
	private T data;

	@Getter
	@Setter
	private ServiceState state;

	public WebServiceResponse() {
		
	}
	
	public WebServiceResponse(T data) {
		setData(data);
	}
	
}
