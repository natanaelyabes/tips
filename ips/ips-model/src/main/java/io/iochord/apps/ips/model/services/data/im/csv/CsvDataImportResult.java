package io.iochord.apps.ips.model.services.data.im.csv;

import io.iochord.apps.ips.common.models.Dataset;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class CsvDataImportResult extends Dataset {
	
	@Getter
	@Setter
	private CsvDataImportConfiguration config;
	
}
