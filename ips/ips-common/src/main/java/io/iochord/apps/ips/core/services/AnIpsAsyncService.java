package io.iochord.apps.ips.core.services;

public abstract class AnIpsAsyncService<C, R> extends AnIpsService<C, R> {
	
	public AnIpsAsyncService() {
		super(true);
	}
	
	public AnIpsAsyncService(boolean async) {
		super(async);
	}
	
}
