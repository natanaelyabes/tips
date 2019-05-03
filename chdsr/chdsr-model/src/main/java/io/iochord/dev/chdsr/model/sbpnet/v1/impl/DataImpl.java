package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import lombok.Getter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class DataImpl extends ElementImpl implements Data {
	@Getter
	private final String elementType = Data.TYPE;
}
