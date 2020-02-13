package io.iochord.apps.ips.model.ism.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.impl.ConfigurationImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = ConfigurationImpl.class)
@JsonTypeName(ElementType.ELEMENT_CONFIGURATION)
public interface Configuration extends Element {
	Map<String, Page> getPages();
}
