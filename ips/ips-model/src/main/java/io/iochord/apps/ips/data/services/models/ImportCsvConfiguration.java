package io.iochord.apps.ips.data.services.models;

import java.io.InputStreamReader;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class ImportCsvConfiguration {
	
	@Getter
	@Setter
	private String title = "";
	
	@Getter
	@Setter
	private char delimeter = '|';
	
	@Getter
	@Setter
	private int headerRowIdx = 0;
	
	@Getter
	@Setter
	private String filename;
	
	@Getter
	@Setter
	@JsonIgnore
	private InputStreamReader reader;
	
}
