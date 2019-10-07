package io.iochord.apps.ips.common.models;

import lombok.Getter;
import lombok.Setter;

public class Identifiable {

	@Getter
	@Setter
	private String identifier = String.valueOf(System.currentTimeMillis());
	
}
