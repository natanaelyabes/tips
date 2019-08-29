package io.iochord.dev.chdsr.simulator.web.v1.models;

import java.util.ArrayList;
import java.util.List;

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
public class WebServiceStatus {
	
	public static WebServiceStatus completed() {
		return new WebServiceStatus("completed");
	}
	
	public static  WebServiceStatus submitted() {
		return new WebServiceStatus("submitted");
	}

	@Getter
	private final List<WebServiceError> errors = new ArrayList<>();

	@Getter
	private final String status;

	@Getter
	@Setter
	private String progressWsUri;

	@Getter
	@Setter
	private String completeWsUri;
	
	public WebServiceStatus(String status) {
		this.status = status;
	}
	
}
