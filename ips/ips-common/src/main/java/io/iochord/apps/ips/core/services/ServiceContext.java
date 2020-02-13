package io.iochord.apps.ips.core.services;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.apps.ips.common.models.Resource;
import io.iochord.apps.ips.common.models.IdentifiableImpl;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-common
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class ServiceContext extends IdentifiableImpl {

	public static final String WS_ENDPOINT = "/ws-ips";
	public static final String WS_REQUEST_URI = "/q";
	public static final String WS_RESPONSE_URI = "/r";
	public static final String WS_RESPONSE_PROGRESS_URI = WS_RESPONSE_URI + "/p";
	public static final String WS_RESPONSE_COMPLETED_URI = WS_RESPONSE_URI + "/c";

	public enum State {
		CREATED, QUEUED, RUNNING, COMPLETED, CANCELED, ERROR,
	}

	@Getter
	@Setter
	@JsonIgnore
	private DataSource dataSource;

	@Getter
	@Setter
	@JsonIgnore
	private SimpMessagingTemplate wsmTemplate;

	@Getter
	@Setter
	@JsonIgnore
	private Map<String, Resource> resources = new TreeMap<>();

	@Getter
	private final ServiceContextInfo info = new ServiceContextInfo();

	@Getter
	@Setter
	private Object data;

	public ServiceContext() {
		setId(String.valueOf(getInfo().getCreated()));
	}

	public void start() {
		getInfo().setStarted(System.currentTimeMillis());
	}

	public <T> void updateProgress(float progress, T data, Date timestamp) {
		getInfo().setProgress(progress);
		getInfo().setProgressData(data);
		getInfo().setTimestamp(timestamp);
		if (getWsmTemplate() == null) {
			return;
		}
		getWsmTemplate().convertAndSend(WS_RESPONSE_PROGRESS_URI + "/" + getId(), getInfo());
	}

	public <T> T completeAndDestroy(State state, int code, T data) {
		getInfo().setState(state);
		getInfo().setCode(code);
		setData(data);
		if (getWsmTemplate() == null) {
			return null;
		}
		getWsmTemplate().convertAndSend(WS_RESPONSE_COMPLETED_URI + "/" + getId(), this);
		return data;
	}

	public void updateProgress(float progress) {
		updateProgress(progress, null, new Date());
	}

	public <T> void updateProgress(T data) {
		updateProgress(getInfo().getProgress(), data, new Date());
	}

	public <T> void updateProgress(float progress, T data) {
		updateProgress(progress, data, new Date());
	}

	public <T> T completeAndDestroy() {
		return completeAndDestroy(State.COMPLETED, 100, null);
	}

	public <T> T completeAndDestroy(T data) {
		return completeAndDestroy(State.COMPLETED, 100, data);
	}
}
