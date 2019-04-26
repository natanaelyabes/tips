package io.iochord.dev.chdsr.model.sbpnet.v1;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.Control;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetImpl;

@JsonDeserialize(as = SbpnetImpl.class)
public interface Sbpnet extends Element {
	public static final String TYPE = "net";

	String getVersion();
	Map<String, Page> getPages();
	Map<String, Configuration> getConfigurations();
	Control getControl();
	Map<String, Data> getData();
}
