package io.iochord.apps.ips.common.models;

import lombok.Getter;
import lombok.Setter;

public class IdentifiableImpl implements Identifiable {

	@Getter
	@Setter
	private String id = String.valueOf(System.currentTimeMillis());
	
}
