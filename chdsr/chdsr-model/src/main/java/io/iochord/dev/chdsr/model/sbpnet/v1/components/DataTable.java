package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.DataTableImpl;

@JsonDeserialize(as = DataTableImpl.class)
public interface DataTable extends Data {
	public static final String TYPE = "datatable";

	Collection<String> getFields();
	Map<String, Map<String, Object>> getData();
}
