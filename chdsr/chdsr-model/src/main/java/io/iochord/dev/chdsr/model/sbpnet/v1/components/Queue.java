package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.QueueImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = QueueImpl.class)
public interface Queue extends Data {
	public static final String TYPE = "queue";

	public enum QUEUE_TYPE {
		LIFO, FIFO,
	}

	QUEUE_TYPE getType();

	boolean isShared();

	boolean isSingle();

	int getSize();
	
	Map<String, Integer> getSizes();
}
