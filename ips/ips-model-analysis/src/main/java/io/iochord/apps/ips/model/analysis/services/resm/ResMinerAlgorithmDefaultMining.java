package io.iochord.apps.ips.model.analysis.services.resm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.iochord.apps.ips.core.services.ServiceContext;

public class ResMinerAlgorithmDefaultMining extends ResMinerAlgorithm {
	
	public ResMinerAlgorithmDefaultMining(ServiceContext context, ResourceMinerConfig config) {
		super(context, config);
	}

	@Override
	void compute() {
		List<String> activities = ModelQuery.getActivities(context, config);
		List<String> resources = ModelQuery.getResources(context, config);
		List<String> groups = new ArrayList<>();
		
		Map<String, List<String>> mActRes = ModelQuery.getDefOrgUnits(context, config);
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
		}
		return mapActToGroup;
	}
	
	public Map<String, List<String>> mapGroupToRes(Map<String, List<String>> mapActToGroup, Map<String, List<String>> mapActToRes) {
		Map<String, List<String>> mapGroupToRes = new HashMap<>();
		for(String activity : mapActToGroup.keySet()) {
			mapGroupToRes.put(mapActToGroup.get(activity).get(0), mapActToRes.get(activity));
		}
		return mapGroupToRes;
	}
}
