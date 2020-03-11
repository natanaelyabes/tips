package io.iochord.apps.ips.model.analysis.services.resm;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.AnIpsAsyncService;
import io.iochord.apps.ips.core.services.ServiceContext;

public class ResourceMinerService extends AnIpsAsyncService<ResourceMinerConfig, ResourceMinerResult> {

	@Override
	public ResourceMinerResult run(ServiceContext context, ResourceMinerConfig config) {
		try (Connection conn = context.getDataSource().getConnection();) {
			
			ResourceMinerResult result = new ResourceMinerResult();
			
			List<String> activities = ModelQuery.getActivities(context, config);
			List<String> resources = ModelQuery.getResources(context, config);
			List<String> groups = new ArrayList<>();
			
			Map<String, List<String>> mActGroup = new LinkedHashMap<>();
			Map<String, List<String>> mGroupRes = new LinkedHashMap<>();
			
			if(config.getResMinAlg().equals("def")) {
				Map<String, List<String>> mActRes = ModelQuery.getDefOrgUnits(context, config);
				Map<String, String> mapActToGroup = mapActToGroup(mActRes.keySet());
				groups = new ArrayList<>(mapActToGroup.values());
				mActGroup = mapActToGroups(mapActToGroup);
				mGroupRes = mapGroupToRes(mapActToGroup, mActRes);
			}
			else if(config.getResMinAlg().equals("dst")) {
				Map<ResourceToActivity, Integer> oacmtrx = ModelQuery.getOrgActMatrix(context, config);
				Map<ResourceToResource,Double> distorig = new HashMap<>();
				
				Map<Integer,Set<String>> mgunitres = new HashMap<>();
				Map<String,Set<Integer>> mactgunit = new HashMap<>();
				
				for(String originatory : resources) {
					for(String originatorx : resources) {
						if(originatory.equals(originatorx))
							continue;
						ResourceToResource key = new ResourceToResource(originatory,originatorx);
						ResourceToResource revkey = new ResourceToResource(originatorx,originatory);
						
						double distVal = distorig.get(revkey) != null ? distorig.get(revkey) : DistanceAlgorithm.calcDistPCC(originatory,originatorx,activities,oacmtrx);
						distorig.put(key, distVal);
						
						Integer gunitc = null;
						boolean isOrig1Exist = false, isOrig2Exist = false;
						for(Integer gunit : mgunitres.keySet())
						{
							boolean isStop = false;
							isOrig1Exist = mgunitres.get(gunit).contains(originatory);
							isStop = isOrig1Exist;
							
							if(distVal >= config.getThreshold()) {
								isOrig2Exist = mgunitres.get(gunit).contains(originatorx);
								isStop = isStop || isOrig2Exist;
							}
							
							if(isStop) {
								gunitc = gunit;
								break;
							}
						}
						
						gunitc = gunitc != null ? gunitc : (mgunitres.size() == 0 ? 1 : Collections.max(mgunitres.keySet())+1); 
						Set<String> sres = mgunitres.getOrDefault(gunitc,new HashSet<String>());
						if(!isOrig1Exist)
							sres.add(originatory);
						if(distVal >= config.getThreshold() && !isOrig2Exist) {
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
				
				groups = mapIntGroupToString(mgunitres.keySet());
				mGroupRes = mapIntGroupToGroupRes(mgunitres);
				mActGroup = mapActIntGroupToActGroup(mactgunit);
			}
			
			result.setActivities(activities);
			result.setResources(resources);
			result.setGroups(groups);
			result.setMactgroup(mActGroup);
			result.setMgroupres(mGroupRes);
			
			context.updateProgress(100, "it is finished");
			
			return result;
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return null;
	}
	
	public List<String> mapIntGroupToString(Set<Integer> groups) {
		List<String> groupSt = new ArrayList<>();
		for(Integer group : groups)
			groupSt.add("orgUnit-"+group);
		return groupSt;
	}
	
	public Map<String, List<String>> mapIntGroupToGroupRes(Map<Integer, Set<String>> mapIntGroupToRes) {
		Map<String, List<String>> mapGroupToRes = new HashMap<>();
		for(Integer group : mapIntGroupToRes.keySet()) {
			mapGroupToRes.put("orgUnit-"+group, new ArrayList<>(mapIntGroupToRes.get(group)));
		}
		return mapGroupToRes;
	}
	
	public Map<String, List<String>> mapActIntGroupToActGroup(Map<String, Set<Integer>> mapActToResInt) {
		Map<String, List<String>> mapActToGroup = new HashMap<>();
		for(String activity : mapActToResInt.keySet()) {
			List<String> groups = new ArrayList<>();
			for(Integer group : mapActToResInt.get(activity))
				groups.add("orgUnit-"+group);
			mapActToGroup.put(activity, groups);
		}
		return mapActToGroup;
	}
	
	public Map<String, String> mapActToGroup(Set<String> activities) {
		Map<String, String> mapActToGroup = new HashMap<>();
		int i = 0;
		for(String activity : activities)
			mapActToGroup.put(activity, "orgUnit-"+(++i));
		return mapActToGroup;
	}
	
	public Map<String, List<String>> mapActToGroups(Map<String, String> mapActToGroup) {
		Map<String, List<String>> mapActToGroups = new HashMap<>();
		for(String activity : mapActToGroup.keySet()) {
			List<String> groups = new ArrayList<>();
			groups.add(mapActToGroup.get(activity));
			mapActToGroups.put(activity, groups);
		}
		return mapActToGroups;
	}
	
	public Map<String, List<String>> mapGroupToRes(Map<String, String> mapActToGroup, Map<String, List<String>> mapActToRes) {
		Map<String, List<String>> mapGroupToRes = new HashMap<>();
		for(String activity : mapActToGroup.keySet()) {
			mapGroupToRes.put(mapActToGroup.get(activity), mapActToRes.get(activity));
		}
		return mapGroupToRes;
	}
}
