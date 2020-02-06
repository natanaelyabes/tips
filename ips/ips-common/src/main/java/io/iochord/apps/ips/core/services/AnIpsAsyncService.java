package io.iochord.apps.ips.core.services;

/**
*
* @package ips-common
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public abstract class AnIpsAsyncService<C, R> extends AnIpsService<C, R> {

	public AnIpsAsyncService() {
		super(true);
	}

	public AnIpsAsyncService(boolean async) {
		super(async);
	}

}
