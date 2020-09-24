package io.iochord.apps.ips.model.services.data.ex.csv;

import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class CsvDataExportConfiguration {
	
	@Getter
	@Setter
	private String datasetId;
	
	@Getter
	@Setter
	private String delimeter = "|";
	
	@Getter
	@Setter
	private String lineBreak = "\r\n";
	
}
