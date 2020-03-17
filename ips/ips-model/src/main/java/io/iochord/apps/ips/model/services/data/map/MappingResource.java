package io.iochord.apps.ips.model.services.data.map;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author N. Y. Wirawan <ny4tips@gmail.com>
 * @since 2020
 * 
 */
public class MappingResource {
	
	@Getter
	@Setter
	private Map<String, String> mapSettings;
	
	@Getter
	@Setter
	private List<String> technicalNames;
	
	@Getter
	@Setter
	private Map<String, String> colHeaders;
	
	@Getter
	@Setter
	private List<Map<String, String>> firstNRows;
	
}
