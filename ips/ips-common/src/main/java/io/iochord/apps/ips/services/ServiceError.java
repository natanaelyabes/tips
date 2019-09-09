package io.iochord.apps.ips.services;

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
public class ServiceError {
	
	public static final ServiceError NONE = new ServiceError("200");

	@Getter
	@Setter
	private String code;
	
	@Getter
	@Setter
	private String message;
	
	public ServiceError() {
		
	}
	
	public ServiceError(String code) {
		setCode(code);
	}
	
	public ServiceError(String code, String message) {
		setCode(code);
		setMessage(message);
	}
}
