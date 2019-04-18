package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ResourceImpl;

@JsonDeserialize(as = ResourceImpl.class)
public interface Resource extends Data {
	public enum RESOURCE_SELECTION {
		RANDOM
	}
	
	String getGroupId();
	DataTable getData();
}
