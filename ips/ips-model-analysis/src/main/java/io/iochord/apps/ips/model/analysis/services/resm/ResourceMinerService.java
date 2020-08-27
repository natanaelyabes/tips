package io.iochord.apps.ips.model.analysis.services.resm;

import java.sql.Connection;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerConfig;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerResult;

public class ResourceMinerService extends AnIpsAsyncService<ResourceMinerConfig, ResourceMinerResult> {

	@Override
	public ResourceMinerResult run(ServiceContext context, ResourceMinerConfig config) {
		try (Connection conn = context.getDataSource().getConnection();) {
			ResourceMinerAlgorithmFactoryImpl resminerImpl = new ResourceMinerAlgorithmFactoryImpl();
			resminerImpl.compute(context, config);
			return resminerImpl.getResult();
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return null;
	}
}
