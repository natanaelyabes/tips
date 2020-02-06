package io.iochord.apps.ips.model.ism.v1.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.Data;
import lombok.Getter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonTypeName(Data.TYPE)
public class DataImpl extends ElementImpl implements Data {

	@Getter
	private final String elementType = Data.TYPE;

}
