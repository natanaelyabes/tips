package io.iochord.apps.ips.model.ism.v1.data;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.common.models.Referenceable;
import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.data.impl.ObjectTypeImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = ObjectTypeImpl.class)
@JsonTypeName(ObjectType.TYPE)
public interface ObjectType extends Data {
	public static final String TYPE = "objecttype";

	@JsonProperty(value = "typesRef")
	Map<String, Referenceable<DataTable>> getTypes();
}
