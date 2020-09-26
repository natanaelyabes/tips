package io.iochord.apps.ips.model.analysis.services.resm;

import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.resm.algorithm.ResMinerAlgorithm;
import io.iochord.apps.ips.model.analysis.services.resm.algorithm.ResMinerAlgorithmDefaultMining;
import io.iochord.apps.ips.model.analysis.services.resm.algorithm.ResMinerAlgorithmDisjointOrgAct;
import io.iochord.apps.ips.model.analysis.services.resm.algorithm.ResMinerAlgorithmDoingSimilarTask;
import io.iochord.apps.ips.model.analysis.services.resm.algorithm.ResourceMinerAlgorithmFactory;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerConfig;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerResult;

public class ResourceMinerAlgorithmFactoryImpl implements ResourceMinerAlgorithmFactory {
	
	public static final String ALGORITHM_DEFAULT_MINING = "def";
	public static final String ALGORITHM_DOING_SIMILAR_TASK = "dst";
	public static final String ALGORITHM_DISJOINT_ORG_ACT = "dis";
	
	private ResMinerAlgorithm resMiner;
	
	@Override
	public void compute(ServiceContext context, ResourceMinerConfig config) {
		if(config.getResMinAlg().equals(ALGORITHM_DEFAULT_MINING))
			resMiner = new ResMinerAlgorithmDefaultMining(context, config);
		if(config.getResMinAlg().equals(ALGORITHM_DOING_SIMILAR_TASK))
			resMiner = new ResMinerAlgorithmDoingSimilarTask(context, config);
		if(config.getResMinAlg().equals(ALGORITHM_DISJOINT_ORG_ACT))
			resMiner = new ResMinerAlgorithmDisjointOrgAct(context, config);
		resMiner.compute();
		if(config.isTimeAnalysis())
			resMiner.timeAnalysis();
	}

	@Override
	public ResourceMinerResult getResult() {
		return resMiner.getResult();
	}
}
