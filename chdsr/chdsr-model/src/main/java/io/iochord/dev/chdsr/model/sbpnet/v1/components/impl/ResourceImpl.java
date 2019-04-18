package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Resource;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

public class ResourceImpl extends DataImpl implements Resource {
	@Getter
	private final String elementType = "resource";

	@Getter @Setter
	private String groupId;

	@Getter @Setter
	private DataTable data;
	
}
