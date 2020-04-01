package io.iochord.apps.ips.model.analysis.services.resm.algorithm;

import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerConfig;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerResult;

public interface ResourceMinerAlgorithmFactory {

	void compute(ServiceContext context, ResourceMinerConfig config);
	
	ResourceMinerResult getResult();
}
