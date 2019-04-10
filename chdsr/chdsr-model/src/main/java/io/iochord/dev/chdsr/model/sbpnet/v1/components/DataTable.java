package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.Collection;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;

public interface DataTable extends Data {
	Collection<String> getFields();
	Map<String, Map<String, Object>> getData();
}
