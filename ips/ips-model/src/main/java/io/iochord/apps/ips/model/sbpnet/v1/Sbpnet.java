package io.iochord.apps.ips.model.sbpnet.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.sbpnet.v1.components.Control;
import io.iochord.apps.ips.model.sbpnet.v1.impl.SbpnetImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = SbpnetImpl.class)
public interface Sbpnet extends Element {
	public static final String TYPE = "net";

	String getVersion();

	Map<String, Page> getPages();
	
	@JsonIgnore
	Page getDefaultPage();

	Map<String, Configuration> getConfigurations();

	Control getControl();

	Map<String, Data> getData();
}