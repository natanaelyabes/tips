package io.iochord.apps.ips.simulator.web.v1.models;

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
public class WebServiceRequest<T> {

	@Getter
	@Setter
	private T config;
	
	public WebServiceRequest() {
		
	}
	
	public WebServiceRequest(T config) {
		setConfig(config);
	}
}
