package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.ObjectType;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
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
		return SbpnetUtil.generateRefs(getTypes());
	}
}
