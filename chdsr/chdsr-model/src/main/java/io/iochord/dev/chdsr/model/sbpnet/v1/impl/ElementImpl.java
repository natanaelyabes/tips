package io.iochord.dev.chdsr.model.sbpnet.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.Element;
import lombok.Getter;
import lombok.Setter;

public class ElementImpl implements Element {

	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private String type;

	@Getter
	@Setter
	private Map<String, String> attributes = new LinkedHashMap<>();

}
