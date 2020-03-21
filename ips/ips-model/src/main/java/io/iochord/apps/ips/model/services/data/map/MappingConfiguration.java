package io.iochord.apps.ips.model.services.data.map;

import lombok.Getter;
import lombok.Setter;

/**
*
* @package ips-model
* @author N. Y. Wirawan <ny4tips@gmail.com>
* @since 2020
* 
*/
public class MappingConfiguration {
	
	@Getter
	@Setter
	private MappingResource resource;
	
	@Getter
	@Setter
	private String datasetId;
}
