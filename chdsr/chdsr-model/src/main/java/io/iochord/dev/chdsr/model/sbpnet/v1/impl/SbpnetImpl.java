package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.Configuration;
import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.Page;
import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet;
import lombok.Getter;
import lombok.Setter;

public class SbpnetImpl extends ElementImpl implements Sbpnet {

	@Getter
	private final String version = "1.0";
	
	@Getter
	private final String elementType = "sbpnet";
	
	@Getter
	@Setter
	private Map<String, Page> pages = new LinkedHashMap<>();
	
	@Getter
	@Setter
	private Map<String, Configuration> configurations = new LinkedHashMap<>();

	@Getter
	@Setter
	private Map<String, Data> data = new LinkedHashMap<>();

}
