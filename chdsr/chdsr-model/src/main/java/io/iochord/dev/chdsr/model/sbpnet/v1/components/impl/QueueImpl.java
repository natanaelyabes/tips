package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Queue;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
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
	private int size = 1;

	@Getter
	@Setter
	private boolean shared = false;
}
