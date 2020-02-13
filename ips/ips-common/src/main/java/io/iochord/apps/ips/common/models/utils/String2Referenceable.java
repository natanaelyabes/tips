package io.iochord.apps.ips.common.models.utils;

import com.fasterxml.jackson.databind.util.StdConverter;

import io.iochord.apps.ips.common.models.Identifiable;
import io.iochord.apps.ips.common.models.Referenceable;

/**
*
* @package ips-common
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class String2Referenceable<V extends Identifiable> extends StdConverter<String, Referenceable<V>> {

	public Referenceable<V> convert(String value) {
		Referenceable<V> ref = new Referenceable<>();
		ref.setValueRef(value);
		return ref;
	}

}
