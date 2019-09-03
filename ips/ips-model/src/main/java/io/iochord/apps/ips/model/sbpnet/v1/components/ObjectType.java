package io.iochord.apps.ips.model.sbpnet.v1.components;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.sbpnet.v1.Data;
import io.iochord.apps.ips.model.sbpnet.v1.components.impl.ObjectTypeImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = ObjectTypeImpl.class)
public interface ObjectType extends Data {
	public static final String TYPE = "objecttype";

	Map<String, DataTable> getTypes();
}
