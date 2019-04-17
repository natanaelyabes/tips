package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.Collection;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

public class DataTableImpl extends DataImpl implements DataTable {
	@Getter
	private final String elementType = "datatable";

	@Getter @Setter
	Collection<String> fields;
	
	@Getter @Setter
	Map<String, Map<String, Object>> data;
}
