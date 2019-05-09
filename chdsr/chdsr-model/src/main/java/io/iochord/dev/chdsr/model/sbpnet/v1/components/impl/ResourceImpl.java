package io.iochord.dev.chdsr.model.sbpnet.v1.components.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.iochord.dev.chdsr.model.sbpnet.v1.components.DataTable;
import io.iochord.dev.chdsr.model.sbpnet.v1.components.Resource;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.DataImpl;
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
public class ResourceImpl extends DataImpl implements Resource {
	@Getter
	private final String elementType = Resource.TYPE;

	@Getter
	@Setter
	private String groupId;

	@Getter
	@Setter
	@JsonIgnore
	private DataTable data;
	
	public String getDataRef() {
		return SbpnetUtil.generateRef(getData());
	}
}
