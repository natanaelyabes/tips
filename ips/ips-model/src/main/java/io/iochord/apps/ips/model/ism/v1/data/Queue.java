package io.iochord.apps.ips.model.ism.v1.data;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.data.impl.QueueImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = QueueImpl.class)
@JsonTypeName(Queue.TYPE)
public interface Queue extends Data {
	public static final String TYPE = "queue";

	public enum QUEUE_TYPE {
		LIFO, FIFO,
	}

	QUEUE_TYPE getType();

	boolean isShared();
	
	
// TODO: Phase 2
//	boolean isSingle();

	int getSize();

	// TODO: Phase 2
//	Map<String, Integer> getSizes();
}
