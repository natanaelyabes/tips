package io.iochord.apps.ips.model.analysis.services.resm.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.resm.model.ModelQuery;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerConfig;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceToActivity;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceToResource;

public class ResMinerAlgorithmDoingSimilarTask extends ResMinerAlgorithm {
	
	public static final String ORG_UNIT = "orgUnit-";
	float updateProg = 0f;
	
	public ResMinerAlgorithmDoingSimilarTask(ServiceContext context, ResourceMinerConfig config) {
		super(context, config);
	}

	@Override
	public void compute() {
		DistanceAlgorithm distAlg = new DistanceAlgorithm();
		
		ModelQuery mq = new ModelQuery(updateProg);
		
		List<String> activities = mq.getActivities(context, config);
		List<String> resources = mq.getResources(context, config);
	
		Map<ResourceToActivity, Integer> oacmtrx = mq.getOrgActMatrix(context, config);
		Map<ResourceToResource, Double> distorig = new HashMap<>();
		
		Map<Integer,Set<String>> mgunitres = new HashMap<>();
		Map<String,Set<Integer>> mactgunit = new HashMap<>();
		
		for(String originatory : resources) {
			for(String originatorx : resources) {
				if(originatory.equals(originatorx))
					continue;
				
				context.updateProgress(updateProg++);
				
				ResourceToResource key = new ResourceToResource(originatory,originatorx);
				ResourceToResource revkey = new ResourceToResource(originatorx,originatory);
				
				double distVal = distorig.get(revkey) != null ? distorig.get(revkey) : distAlg.calcDist(config.getDistMesAlg(),originatory,originatorx,activities,oacmtrx);
				distorig.put(key, distVal);
				
				Integer gunitc = null;
				boolean isOrig1Exist = false;
				boolean isOrig2Exist = false;
				
				for(Entry<Integer, Set<String>> entryset : mgunitres.entrySet())
				{
					boolean isStop = false;
					isOrig1Exist = mgunitres.get(entryset.getKey()).contains(originatory);
					isStop = isOrig1Exist;
					
					if(distAlg.evalThreshold(distVal, config.getThreshold(), config.getDistMesAlg())) {
						isOrig2Exist = mgunitres.get(entryset.getKey()).contains(originatorx);
						isStop = isStop || isOrig2Exist;
					}
					
					if(isStop) {
						gunitc = entryset.getKey();
						break;
					}
				}
				
				gunitc = gunitc != null ? gunitc : (mgunitres.size() == 0 ? 1 : Collections.max(mgunitres.keySet())+1); 
				Set<String> sres = mgunitres.getOrDefault(gunitc, new HashSet<String>());
				if(!isOrig1Exist)
					sres.add(originatory);
				if(distAlg.evalThreshold(distVal, config.getThreshold(), config.getDistMesAlg()) && !isOrig2Exist) {
					sres.add(originatorx);
				}
				
				mgunitres.put(gunitc, sres);
				
				for(ResourceToActivity oac : oacmtrx.keySet()) {
					if(oac.getResource().equals(originatory)) {
						Set<Integer> sgunits = mactgunit.getOrDefault(oac.getActivity(), new HashSet<Integer>());
						sgunits.add(gunitc);
						mactgunit.put(oac.getActivity(), sgunits);
					}
				}
			}
		}
		
		context.updateProgress(updateProg++);
		
		List<String> groups = mapIntGroupToString(mgunitres.keySet());
		Map<String, List<String>> mGroupRes = mapIntGroupToGroupRes(mgunitres);
		Map<String, List<String>> mActGroup = mapActIntGroupToActGroup(mactgunit);
		
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
			groups.add(ORG_UNIT+(++i));
			mapActToGroup.put(activity, groups);
			context.updateProgress(updateProg++);
		}
		return mapActToGroup;
	}
	
	public List<String> mapIntGroupToString(Set<Integer> groups) {
		List<String> groupSt = new ArrayList<>();
		for(Integer group : groups)
			groupSt.add(ORG_UNIT+group);
		return groupSt;
	}
	
	public Map<String, List<String>> mapIntGroupToGroupRes(Map<Integer, Set<String>> mapIntGroupToRes) {
		Map<String, List<String>> mapGroupToRes = new HashMap<>();
		for(Integer group : mapIntGroupToRes.keySet()) {
			mapGroupToRes.put(ORG_UNIT+group, new ArrayList<>(mapIntGroupToRes.get(group)));
			context.updateProgress(updateProg++);
		}
		return mapGroupToRes;
	}
	
	public Map<String, List<String>> mapActIntGroupToActGroup(Map<String, Set<Integer>> mapActToResInt) {
		Map<String, List<String>> mapActToGroup = new HashMap<>();
		for(String activity : mapActToResInt.keySet()) {
			List<String> groups = new ArrayList<>();
			for(Integer group : mapActToResInt.get(activity))
				groups.add(ORG_UNIT+group);
			mapActToGroup.put(activity, groups);
		}
		return mapActToGroup;
	}
}
