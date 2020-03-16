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
	private List<Map<String, String>> mapSettings;
	
	@Getter
	@Setter
	private List<String> technicalNames;
	
	@Getter
	@Setter
	private List<Map<String, String>> firstNRows;
	
	public void printToConsole() {
		for (Map<String, String> map : mapSettings) {
			System.out.println(map.keySet());
			System.out.println(map.entrySet());
		}
		
		for (String row : technicalNames) {
			System.out.println(row);
		}
	}
	
}
