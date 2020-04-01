package io.iochord.apps.ips.model.analysis.services.resm.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.resm.model.ModelQuery;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerConfig;

public class ResMinerAlgorithmDefaultMining extends ResMinerAlgorithm {
	
	float updateProg = 0f;
	
	public ResMinerAlgorithmDefaultMining(ServiceContext context, ResourceMinerConfig config) {
		super(context, config);
	}

	@Override
	public void compute() {
		ModelQuery mq = new ModelQuery(updateProg);
		
		List<String> activities = mq.getActivities(context, config);
		List<String> resources = mq.getResources(context, config);
		Map<String, List<String>> mActRes = mq.getDefOrgUnits(context, config);
		
		List<String> groups = new ArrayList<>();
		
		context.updateProgress(updateProg++);
		
		Map<String, List<String>> mActGroup = mapActToGroup(mActRes.keySet());
		for(List<String> group : mActGroup.values())
			groups.addAll(group);
		Map<String, List<String>> mGroupRes = mapGroupToRes(mActGroup, mActRes);
		
		Map<String, List<String>> mGroupAct = reverseMapping(mActGroup, groups);
		Map<String, List<String>> mResGroup = reverseMapping(mGroupRes, resources);
		
		result.setActivities(activities);
		result.setResources(resources);
		result.setGroups(groups);
		result.setMactgroup(mActGroup);
		result.setMgroupres(mGroupRes);
		result.setMgroupact(mGroupAct);
		result.setMresgroup(mResGroup);
	}
	
	public Map<String, List<String>> mapActToGroup(Set<String> activities) {
		Map<String, List<String>> mapActToGroup = new HashMap<>();
		int i = 0;
		for(String activity : activities) {
			List<String> groups = new ArrayList<>();
			groups.add("orgUnit-"+(++i));
			mapActToGroup.put(activity, groups);
			context.updateProgress(updateProg++);
		}
		return mapActToGroup;
	}
	
	public Map<String, List<String>> mapGroupToRes(Map<String, List<String>> mapActToGroup, Map<String, List<String>> mapActToRes) {
		Map<String, List<String>> mapGroupToRes = new HashMap<>();
		for(String activity : mapActToGroup.keySet()) {
			mapGroupToRes.put(mapActToGroup.get(activity).get(0), mapActToRes.get(activity));
			context.updateProgress(updateProg++);
		}
		return mapGroupToRes;
	}
}
