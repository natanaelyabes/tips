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
public class WebServiceError {
	
	public static final WebServiceError NONE = new WebServiceError("200");

	@Getter
	@Setter
	private String code;
	
	@Getter
	@Setter
	private String message;
	
	public WebServiceError() {
		
	}
	
	public WebServiceError(String code) {
		setCode(code);
	}
	
	public WebServiceError(String code, String message) {
		setCode(code);
		setMessage(message);
	}
}
