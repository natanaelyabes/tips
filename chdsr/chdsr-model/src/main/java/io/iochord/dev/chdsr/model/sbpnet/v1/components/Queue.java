package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.QueueImpl;

@JsonDeserialize(as = QueueImpl.class)
public interface Queue extends Data {
	public enum QUEUE_TYPE {
		LIFO,
		FIFO,
	}
	
	QUEUE_TYPE getType();
	int getSize();
	boolean isShared();
}
