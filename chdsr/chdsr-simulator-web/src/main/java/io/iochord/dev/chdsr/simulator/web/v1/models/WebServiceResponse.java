package io.iochord.dev.chdsr.simulator.web.v1.models;

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
	private WebServiceStatus status = WebServiceStatus.completed();
	
	public WebServiceResponse() {
		
	}
	
	public WebServiceResponse(T data) {
		setData(data);
	}
	
}
