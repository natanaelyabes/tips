package io.iochord.apps.ips.model.ism.v1.data.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import lombok.Getter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ObjectType.TYPE)
public class ObjectTypeImpl extends DataImpl implements ObjectType {
	@Getter
	private final String elementType = ObjectType.TYPE;
	
//	@Getter
//	@Setter
//	private Map<String, Referenceable<DataTable>> types = new LinkedHashMap<>();
}
