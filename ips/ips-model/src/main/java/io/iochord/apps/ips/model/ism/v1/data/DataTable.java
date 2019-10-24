package io.iochord.apps.ips.model.ism.v1.data;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.iochord.apps.ips.model.ism.v1.Data;
import io.iochord.apps.ips.model.ism.v1.data.impl.DataTableImpl;

/**
 *
 * @package chdsr-model
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@JsonDeserialize(as = DataTableImpl.class)
@JsonTypeName(DataTable.TYPE)
public interface DataTable extends Data {
	public static final String TYPE = "datatable";

	Map<String, String> getFields();

	Map<String, Map<String, Object>> getData();
	
}
