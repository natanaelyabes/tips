package io.iochord.apps.ips.model.sbpnet.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import io.iochord.apps.ips.model.sbpnet.v1.Configuration;
import io.iochord.apps.ips.model.sbpnet.v1.Data;
import io.iochord.apps.ips.model.sbpnet.v1.Page;
import io.iochord.apps.ips.model.sbpnet.v1.Sbpnet;
import io.iochord.apps.ips.model.sbpnet.v1.components.Control;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class SbpnetImpl extends ElementImpl implements Sbpnet {
	@Getter
	private final String version = "1.0";

	@Getter
	private final String elementType = Sbpnet.TYPE;

	@Getter
	@Setter
	private Map<String, Page> pages = new LinkedHashMap<>();

	@Getter
	@Setter
	private Map<String, Configuration> configurations = new LinkedHashMap<>();

	@Getter
	@Setter
	private Control control;

	@Getter
	@Setter
	private Map<String, Data> data = new LinkedHashMap<>();

	@Override
	public Page getDefaultPage() {
		if (getPages() != null) {
			return getPages().values().iterator().next();
		}
		return null;
	}
}