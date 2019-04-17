package io.iochord.dev.chdsr.model.sbpnet.v1;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetImpl;

@JsonDeserialize(as = SbpnetImpl.class)
public interface Sbpnet extends Element {
	String getVersion();
	Map<String, Page> getPages();
	Map<String, Configuration> getConfigurations();
	Map<String, Data> getData();
}
