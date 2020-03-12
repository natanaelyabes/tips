package io.iochord.apps.ips.model.analysis.services.resm;

import io.iochord.apps.ips.core.services.ServiceContext;

interface ResourceMinerAlgorithmFactory {

	void compute(ServiceContext context, ResourceMinerConfig config);
	
	ResourceMinerResult getResult();
}
