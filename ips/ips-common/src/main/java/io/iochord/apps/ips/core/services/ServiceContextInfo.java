package io.iochord.apps.ips.core.services;

import java.util.Date;

import io.iochord.apps.ips.core.services.ServiceContext.State;
import lombok.Getter;
import lombok.Setter;

public class ServiceContextInfo {
	
	@Getter
	@Setter
	private State state = State.CREATED;
	
	@Getter
	@Setter
	private int code;

	@Getter
	private final long created = System.currentTimeMillis();
	
	@Getter
	@Setter
	private long started = -1;
	
	@Getter
	@Setter
	private Date timestamp;
	
	@Getter
	@Setter
	private float progress;
	
	@Getter
	@Setter
	private Object progressData;

	public long getElapsedTime() {
		return System.currentTimeMillis() - getCreated();
	}
	
	public long getCompletionTime() {
		if (getStarted() < 0) {
			setStarted(getCreated());
		}
		return System.currentTimeMillis() - getStarted();
	}

}
