package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ObjectTypeImpl;

@JsonDeserialize(as = ObjectTypeImpl.class)
public interface ObjectType extends Data {
	public static final String TYPE = "objecttype";

	Map<String, DataTable> getFields();
}
