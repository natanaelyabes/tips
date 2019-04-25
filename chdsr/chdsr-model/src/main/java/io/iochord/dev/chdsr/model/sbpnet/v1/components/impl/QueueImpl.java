package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Queue;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

public class QueueImpl extends DataImpl implements Queue {
	@Getter
	private final String elementType = "queue";

	@Getter @Setter
	private QUEUE_TYPE type;

	@Getter @Setter
	private int size;

	@Getter @Setter
	private boolean shared;
}
