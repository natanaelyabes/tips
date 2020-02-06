package io.iochord.apps.ips.common.models;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.iochord.apps.ips.common.models.utils.Referenceable2String;
import io.iochord.apps.ips.common.models.utils.String2Referenceable;
import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-common
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
@JsonSerialize(converter = Referenceable2String.class)
@JsonDeserialize(converter = String2Referenceable.class)
public class Referenceable<V extends Identifiable> extends IdentifiableImpl {

	public Referenceable() {

	}

	public Referenceable(V object) {
		setValue(object);
		if (object != null) {
			setValueRef(object.getId());
		}
	}

	private String valueRef;

	public String getId() {
		if (getValue() != null) {
			return getValue().getId();
		}
		return super.getId();
	}

	@Getter
	@Setter
	private Map<String, ? extends Identifiable> valueRepository;

	@Setter
	private V value;

	@SuppressWarnings("unchecked")
	public V getValue() {
		if (value != null) {
			return value;
		} else if (valueRef != null && getValueRepository() != null) {
			if (getValueRepository().containsKey(valueRef)) {
				value = (V) getValueRepository().get(valueRef);
			}
		}
		return value;
	}

	public String getValueRef() {
		if (value != null) {
			return value.getId();
		} else {
			return valueRef;
		}
	}

	public void setValueRef(String valueRef) {
		this.valueRef = valueRef;
	}

	public <E extends V> E getValueAs(Class<E> clazz) {
		if (getValue() != null && clazz.isInstance(getValue())) {
			return clazz.cast(getValue());
		}
		return null;
	}

}
