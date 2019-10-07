package io.iochord.apps.ips.core.services;

import io.iochord.apps.ips.common.models.Identifiable;
import lombok.Getter;

public abstract class AnIpsService<C, R> extends Identifiable {
	
	@Getter
	private final boolean async;
	
	public AnIpsService() {
		this.async = false;
	}
	
	public AnIpsService(boolean async) {
		this.async = async;
	}
	
	public abstract R run(ServiceContext context, C config) throws Exception;
	
}
