package io.iochord.apps.ips.model.ism.v1.data;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.ElementType;
import io.iochord.apps.ips.model.ism.v1.data.impl.ObjectTypeImpl;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonDeserialize(as = ObjectTypeImpl.class)
@JsonTypeName(ElementType.DATA_OBJECTTYPE)
public interface ObjectType extends Data {
}
