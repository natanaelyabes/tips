package io.iochord.apps.ips.core.services;

import io.iochord.apps.ips.common.models.IdentifiableImpl;
import lombok.Getter;

/**
*
* @package ips-common
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public abstract class AnIpsService<C, R> extends IdentifiableImpl {

	@Getter
	private final boolean async;

	public AnIpsService() {
		this.async = false;
	}

	public AnIpsService(boolean async) {
		this.async = async;
	}

	public abstract R run(ServiceContext context, C config);
}
