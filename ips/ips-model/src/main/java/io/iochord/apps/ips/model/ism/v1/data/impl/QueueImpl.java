package io.iochord.apps.ips.model.ism.v1.data.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.data.Queue;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonTypeName(Queue.TYPE)
public class QueueImpl extends DataImpl implements Queue {
	@Getter
	private final String elementType = Queue.TYPE;
	
	@Getter
	@Setter
	private QUEUE_TYPE type = QUEUE_TYPE.FIFO;

	@Getter
	@Setter
	private boolean shared = false;


	@Getter
	@Setter
	private boolean single = true;
	
	@Getter
	@Setter
	private int size = -1;
	
	@Getter
	@Setter
	private Map<String, Integer> sizes = new LinkedHashMap<>();
}
