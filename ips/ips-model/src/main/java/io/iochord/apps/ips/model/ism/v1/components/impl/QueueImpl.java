package io.iochord.apps.ips.model.ism.v1.components.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.model.ism.v1.components.Queue;
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