package io.iochord.apps.ips.services;

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
public class ServiceState {
	
	public enum STATE {
		SUBMITTED,
		COMPLETED,
	}

	@Getter
	private final List<ServiceError> errors = new ArrayList<>();

	@Getter
	private final String sessionId;
	
	@Getter
	@Setter
	private STATE status = STATE.SUBMITTED;
	
	@Getter
	@Setter
	private String statusRemarks;

	@Getter
	@Setter
	private String progressWsUri;

	@Getter
	@Setter
	private String completeWsUri;
	
	public ServiceState(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
