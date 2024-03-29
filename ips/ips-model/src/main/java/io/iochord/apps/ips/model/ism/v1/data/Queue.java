package io.iochord.apps.ips.model.ism.v1.data;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.data.impl.QueueImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = QueueImpl.class)
@JsonTypeName(ElementType.DATA_QUEUE)
public interface Queue extends Data {

	public enum QUEUE_TYPE {
		LIFO, FIFO,
	}

	QUEUE_TYPE getType();

	boolean isShared();

	int getSize();
}
