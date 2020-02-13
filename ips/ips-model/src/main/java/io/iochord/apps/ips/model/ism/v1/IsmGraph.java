package io.iochord.apps.ips.model.ism.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.impl.IsmGraphImpl;
import io.iochord.apps.ips.model.ism.v1.nodes.Control;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = IsmGraphImpl.class)
@JsonTypeName(ElementType.ELEMENT_NET)
public interface IsmGraph extends Element {

	String getVersion();

	Map<String, Page> getPages();

	@JsonIgnore
	Page getDefaultPage();

	Map<String, Configuration> getConfigurations();

	Control getControl();

	void loadReferences();
}
