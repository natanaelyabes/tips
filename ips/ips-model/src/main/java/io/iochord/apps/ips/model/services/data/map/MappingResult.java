package io.iochord.apps.ips.model.services.data.map;

import io.iochord.apps.ips.common.models.Dataset;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @package ips-model
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2020
 * 
 */
public class MappingResult extends Dataset {
	
	@Getter
	@Setter
	private MappingConfiguration config;
}
