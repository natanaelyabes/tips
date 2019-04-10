package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;

public interface Declaration extends Data {
	Map<String, DataTable> getTypes();
}
