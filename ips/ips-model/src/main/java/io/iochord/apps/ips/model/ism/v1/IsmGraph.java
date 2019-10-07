package io.iochord.apps.ips.model.ism.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.components.Control;
import io.iochord.apps.ips.model.ism.v1.impl.IsmImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = IsmImpl.class)
public interface IsmGraph extends Element {
	public static final String TYPE = "net";

	String getVersion();

	Map<String, Page> getPages();
	
	@JsonIgnore
	Page getDefaultPage();

	Map<String, Configuration> getConfigurations();

	Control getControl();

	Map<String, Data> getData();
}
