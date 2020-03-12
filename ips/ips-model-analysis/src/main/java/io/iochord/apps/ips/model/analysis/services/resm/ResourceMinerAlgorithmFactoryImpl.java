package io.iochord.apps.ips.model.analysis.services.resm;

import io.iochord.apps.ips.core.services.ServiceContext;

public class ResourceMinerAlgorithmFactoryImpl implements ResourceMinerAlgorithmFactory {
	
	public static final String ALGORITHM_DEFAULT_MINING = "def";
	public static final String ALGORITHM_DOING_SIMILAR_TASK = "dst";
	
	private ResMinerAlgorithm resMiner;
	
	@Override
	public void compute(ServiceContext context, ResourceMinerConfig config) {
		if(config.getResMinAlg().equals(ALGORITHM_DEFAULT_MINING))
			resMiner = new ResMinerAlgorithmDefaultMining(context, config);
		if(config.getResMinAlg().equals(ALGORITHM_DOING_SIMILAR_TASK))
			resMiner = new ResMinerAlgorithmDoingSimilarTask(context, config);
		
		resMiner.compute();
	}

	@Override
	public ResourceMinerResult getResult() {
		return resMiner.getResult();
	}

}
