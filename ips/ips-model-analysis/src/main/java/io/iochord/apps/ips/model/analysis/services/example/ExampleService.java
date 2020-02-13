package io.iochord.apps.ips.model.analysis.services.example;

import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;

public class ExampleService extends AnIpsAsyncService<ExampleConfig, ExampleResult> {

	@Override
	public ExampleResult run(ServiceContext context, ExampleConfig config) {
		return null;
	}

}
