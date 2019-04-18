package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Declaration;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

public class DeclarationImpl extends DataImpl implements Declaration {
	@Getter
	private final String elementType = "declaration";

	@Getter @Setter
	Map<String, DataTable> fields;
}
