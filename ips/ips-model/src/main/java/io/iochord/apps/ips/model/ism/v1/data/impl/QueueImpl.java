package io.iochord.apps.ips.model.ism.v1.data.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.DATA_QUEUE)
public class QueueImpl extends DataImpl implements Queue {
	
	private QUEUE_TYPE queueType = QUEUE_TYPE.FIFO;

	@Getter
	@Setter
	private boolean shared = false;

	@Getter
	@Setter
	private int size = -1;

	public String getElementType() {
		return ElementType.DATA_QUEUE;
	}

	@Override
	public QUEUE_TYPE getType() {
		return queueType;
	}
	
	public void setType(QUEUE_TYPE queueType) {
		this.queueType = queueType;
	}

}
