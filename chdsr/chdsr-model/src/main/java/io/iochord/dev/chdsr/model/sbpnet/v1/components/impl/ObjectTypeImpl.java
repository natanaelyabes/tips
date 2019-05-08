package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import java.util.Map;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.ObjectType;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
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
	Map<String, DataTable> fields;
}
