package io.iochord.apps.ips.model.services.data.im.xes;

import io.iochord.apps.ips.common.models.Dataset;
import lombok.Getter;
import lombok.Setter;

/**
 * XES import result object.
 *
 * @package ips-model
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2020
 * 
 */
public class XesDataImportResult extends Dataset {
	
	@Getter
	@Setter
	private XesDataImportConfiguration config;

}
