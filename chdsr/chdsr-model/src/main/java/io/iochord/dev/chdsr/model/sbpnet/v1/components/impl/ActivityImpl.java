package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.concurrent.TimeUnit;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Activity;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Function;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Queue;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Resource;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.NodeImpl;
import lombok.Getter;
import lombok.Setter;

public class ActivityImpl extends NodeImpl implements Activity {
	@Getter
	private final String elementType = Activity.TYPE;

	@Getter
	@Setter
	private ACTIVITY_TYPE type;

	@Getter
	@Setter
	private Resource resource;

	@Getter
	@Setter
	private Queue queue;

	@Getter
	@Setter
	private Function function;

	@Getter
	@Setter
	private String setupTimeExpression;

	@Getter
	@Setter
	private String processingTimeExpression;

	@Getter
	@Setter
	private TimeUnit unit;

}
