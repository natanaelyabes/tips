package io.iochord.apps.ips.data.services.models;

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
public class ImportCsvResult {
	
	@Getter
	@Setter
	private ImportCsvConfiguration config;
	
	@Getter
	@Setter
	private String name;
	
}
