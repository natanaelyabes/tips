package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;

public interface Resource extends Data {
	String getGroupId();
	DataTable getData();
}
