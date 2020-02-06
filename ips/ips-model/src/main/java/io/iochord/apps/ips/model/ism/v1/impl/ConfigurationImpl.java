package io.iochord.apps.ips.model.ism.v1.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Configuration;
import io.iochord.apps.ips.model.ism.v1.Page;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(Configuration.TYPE)
public class ConfigurationImpl extends ElementImpl implements Configuration {
	@Getter
	private final String elementType = Configuration.TYPE;

	@Getter
	@Setter
	private Map<String, Page> pages = new LinkedHashMap<>();
}
