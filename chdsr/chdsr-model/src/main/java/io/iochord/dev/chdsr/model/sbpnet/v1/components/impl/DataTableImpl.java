package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

public class DataTableImpl extends DataImpl implements DataTable {
	@Getter
	private final String elementType = DataTable.TYPE;

	@Getter
	@Setter
	private Map<String, String> fields = new LinkedHashMap<>();

	@Getter
	@Setter
	private Map<String, Map<String, Object>> data = new LinkedHashMap<>();
}
