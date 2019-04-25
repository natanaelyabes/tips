package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import lombok.Getter;

public class DataImpl extends ElementImpl implements Data {
	@Getter
	private final String elementType = Data.TYPE;

}
