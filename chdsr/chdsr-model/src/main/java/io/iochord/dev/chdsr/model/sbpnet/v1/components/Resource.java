package io.iochord.dev.chdsr.model.sbpnet.v1.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.dev.chdsr.model.sbpnet.v1.Data;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.impl.ResourceImpl;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
@JsonDeserialize(as = ResourceImpl.class)
public interface Resource extends Data {
	public static final String TYPE = "resource";

	public enum RESOURCE_SELECTION {
		RANDOM
	}

	String getGroupId();

	DataTable getData();
}
