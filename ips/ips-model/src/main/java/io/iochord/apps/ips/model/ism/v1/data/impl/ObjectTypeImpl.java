package io.iochord.apps.ips.model.ism.v1.data.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.data.ObjectType;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(ElementType.DATA_OBJECTTYPE)
public class ObjectTypeImpl extends DataImpl implements ObjectType {

	public String getElementType() {
		return ElementType.DATA_OBJECTTYPE;
	}

}
