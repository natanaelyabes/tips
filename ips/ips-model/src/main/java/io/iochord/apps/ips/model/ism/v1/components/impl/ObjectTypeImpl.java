package io.iochord.apps.ips.model.ism.v1.components.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.apps.ips.model.ism.v1.components.DataTable;
import io.iochord.apps.ips.model.ism.v1.components.ObjectType;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import io.iochord.apps.ips.model.ism.v1.impl.IsmUtil;
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
public class ObjectTypeImpl extends DataImpl implements ObjectType {
	@Getter
	private final String elementType = ObjectType.TYPE;

	@Getter
	@Setter
	@JsonIgnore
	private Map<String, DataTable> types = new LinkedHashMap<>();

	// TODO: Deserialize this one back from ref
	public Map<String, String> getTypeRefs() {
		return IsmUtil.generateRefs(getTypes());
	}
}