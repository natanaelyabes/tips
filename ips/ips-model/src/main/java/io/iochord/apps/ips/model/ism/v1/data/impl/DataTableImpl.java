package io.iochord.apps.ips.model.ism.v1.data.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.iochord.apps.ips.model.ism.v1.data.DataTable;
import io.iochord.apps.ips.model.ism.v1.impl.DataImpl;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonTypeName(DataTable.TYPE)
public class DataTableImpl extends DataImpl implements DataTable {
	@Getter
	private final String elementType = DataTable.TYPE;
	
	@Getter
	@Setter
	private Map<String, String> fields = new LinkedHashMap<>();

	@Getter
	@Setter
	private Map<String, Map<String, Object>> data = new LinkedHashMap<>();
}
