package io.iochord.apps.ips.common.models.utils;

import com.fasterxml.jackson.databind.util.StdConverter;

import io.iochord.apps.ips.common.models.Identifiable;
import io.iochord.apps.ips.common.models.Referenceable;

public class Referenceable2String<V extends Identifiable> extends StdConverter<Referenceable<V>, String> {

	@Override
	public String convert(Referenceable<V> value) {
		return value.getValueRef();
	}
}
