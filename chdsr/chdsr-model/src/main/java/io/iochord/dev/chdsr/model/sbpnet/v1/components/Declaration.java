package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.DeclarationImpl;

@JsonDeserialize(as = DeclarationImpl.class)
public interface Declaration extends Data {
	Map<String, DataTable> getFields();
}
