package io.iochord.apps.ips.model.analysis.services.resm.algorithm;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerConfig;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceToResource;

public class ResMinerAlgorithmDoingSimilarTaskDbComp extends ResMinerAlgorithm {
	// Doing all computation in DB proven can compute the result faster
	// Already test it
	List<String> activities = null;
	List<String> resources = new ArrayList<>();
	List<String> groups = new ArrayList<>();
	
	Map<ResourceToResource, Double> distorig = new HashMap<>();
	
	Map<String, List<String>> mgroupact = new HashMap<>();
	Map<String, List<String>> mgroupres = new HashMap<>();
	Map<String, List<String>> orgtoact = new HashMap<>();
	
	int refgroupint = 0;
	
	public static final String ORG_UNIT = "orgUnit-";
	float updateProg = 0f;
	
	public ResMinerAlgorithmDoingSimilarTaskDbComp(ServiceContext context, ResourceMinerConfig config) {
		super(context, config);
	}

	@Override
	public void compute() {
		//context.updateProgress(updateProg++);
		
		setResMinerOutput(context, config);
		
		int startEval = 0;
		for(String resource : resources) {
			int refStartEval = 0;
			Optional<Entry<String, List<String>>> optres = mgroupres.entrySet().stream().filter(e -> e.getValue().contains(resource)).findFirst();
			List<String> distres;
			String groupid;
			try {
				Entry<String, List<String>> es = optres.get();
				groupid = es.getKey();
				distres = es.getValue();
			}
			catch(NoSuchElementException e) {
				groupid = ORG_UNIT + ++refgroupint;
				distres = createNewGroup(resource,groupid);
				setMapActGroup(resource, orgtoact, groupid);
			}
			
			for(String refresource : resources) {
				if(refStartEval >= startEval && !resource.equals(refresource)) {
					if(distorig.get(new ResourceToResource(resource,refresource)) >= config.getThreshold()) {
						if(!distres.contains(refresource))
							distres.add(refresource);
						setMapActGroup(resource, orgtoact, groupid);
					}
				}
				refStartEval++;
			}
			startEval++;
		}
		
		result.setActivities(activities);
		result.setResources(resources);
		result.setGroups(groups);
		result.setMgroupact(mgroupact);
		result.setMgroupres(mgroupres);
		result.setMactgroup(reverseMapping(mgroupact, null));
		result.setMresgroup(reverseMapping(mgroupres, null));
	}
	
	public void setMapActGroup(String resource, Map<String, List<String>> orgToAct, String groupid) {
		List<String> acts = orgToAct.get(resource);
		List<String> actsgroup = mgroupact.getOrDefault(groupid, new ArrayList<>());
		actsgroup.addAll(acts);
		mgroupact.put(groupid, actsgroup);
	}
	
	public List<String> createNewGroup(String resource, String groupid) {
		List<String> newset = new ArrayList<>();
		newset.add(resource);
		mgroupres.put(ORG_UNIT + refgroupint, newset);
		groups.add(groupid);
		return newset;
	}
	
	public void setResMinerOutput(ServiceContext context, ResourceMinerConfig config) {
		try (Connection conn = context.getDataSource().getConnection();) {
			String query =
					"with orgactmtrx as ( "+
					"  select b3.orig, b3.act, "+
					"  case "+
					"    when freq is null then 0 "+
					"    else b4.freq "+
					"  end as freq "+
					"  from ( "+
					"    (select b1.orig, b2.act "+
					"    from "+
					"      (select distinct "+config.getColEventResource()+" as orig from "+config.getDatasetId()+") as b1, "+
					"      (select distinct "+config.getColEventActivity()+" as act from "+config.getDatasetId()+") as b2) as b3 "+
					"    left join "+
					"      (select "+config.getColEventResource()+" as orig, "+config.getColEventActivity()+" as act, count("+config.getColEventResource()+") as freq from "+config.getDatasetId()+" group by "+config.getColEventResource()+", "+config.getColEventActivity()+") as b4 "+
				    "    on b3.orig = b4.orig and b3.act = b4.act "+
					"    ) "+
					"), orgactmtrxfull as ( "+
					"  select o1.orig as orig1, o2.orig as orig2, o1.act, "+
			        "  o1.freq as freq1, o2.freq as freq2, "+
					"  sum(o1.freq) over(partition by o1.orig, o2.orig) as sumfreq1, "+
					"  sum(o2.freq) over(partition by o1.orig, o2.orig) as sumfreq2, "+
					"  cast(count(o1.act) over(partition by o1.orig, o2.orig) as float) as countact "+
					"  from "+
					"    orgactmtrx as o1, "+
					"    orgactmtrx as o2 "+
					"  where o1.orig != o2.orig and o1.act = o2.act "+
					"), distmtrx as ( "+
					"  select orig1, orig2, "+
					"  sum((freq1-(sumfreq1/countact))*(freq2-sumfreq2/countact))/sqrt(sum(power(freq1-(sumfreq1/countact),2))*sum(power(freq2-(sumfreq2/countact),2))) as dist, "+
					"  array_agg(distinct act) as arract, "+
					"  array_remove(array_agg(distinct case when freq1 > 0 then act end), NULL) as mapactorig "+
					"  from "+ 
					"    orgactmtrxfull "+
					"  group by orig1, orig2 "+
					") "+
					"select orig1, orig2, arract, mapactorig, dist from distmtrx";
			
			try (PreparedStatement st = conn.prepareStatement(query);
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					
					String resource1 = rs.getString("orig1");
					String resource2 = rs.getString("orig2");
					float distance = rs.getFloat("dist");
					
					if(activities == null) {
						Array array = rs.getArray("arract");
						activities = Arrays.asList(array.getArray()).stream().map(String.class::cast).collect(Collectors.toList());
					}
					
					if(orgtoact.get(resource1) == null) {
						Array array = rs.getArray("mapactorig");
						orgtoact.put(resource1, Arrays.asList(array.getArray()).stream().map(String.class::cast).collect(Collectors.toList()));
					}
					
					distorig.put(new ResourceToResource(resource1,resource2), (double) distance);
					if(!resources.contains(resource1))
						resources.add(resource1);
					//context.updateProgress(updateProg++);
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
	}
}