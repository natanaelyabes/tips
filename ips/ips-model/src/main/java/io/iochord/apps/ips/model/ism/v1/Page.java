package io.iochord.apps.ips.model.ism.v1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.impl.PageImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = PageImpl.class)
@JsonTypeName(ElementType.ELEMENT_PAGE)
public interface Page extends Element {

	Map<String, Data> getData();

	Map<String, Node> getNodes();

	Map<String, Connector> getConnectors();
}
