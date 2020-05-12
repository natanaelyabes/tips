package io.iochord.apps.ips.model.analysis.services.resm.algorithm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.core.services.ServiceContext;
import io.iochord.apps.ips.model.analysis.services.resm.model.EventWorkInfo;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerConfig;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceMinerResult;
import io.iochord.apps.ips.model.analysis.services.resm.model.ResourceToShiftNumb;
import io.iochord.apps.ips.model.analysis.services.resm.model.TimeUnit;
import io.iochord.apps.ips.model.analysis.services.resm.model.UnitDist;

public abstract class ResMinerAlgorithm {
	ServiceContext context;
	ResourceMinerConfig config;
	ResourceMinerResult result = new ResourceMinerResult();
	
	public ResMinerAlgorithm(ServiceContext context, ResourceMinerConfig config) {
		this.context = context;
		this.config = config;
	}
	
	public abstract void compute();
	
	public void timeAnalysis() {
		int icol = 0;
		String[] colValues = new String[] { 
	        "FF0000", "00FF00", "0000FF", "FFFF00", "FF00FF", "00FFFF", "000000", 
	        "800000", "008000", "000080", "808000", "800080", "008080", "808080", 
	        "C00000", "00C000", "0000C0", "C0C000", "C000C0", "00C0C0", "C0C0C0", 
	        "400000", "004000", "000040", "404000", "400040", "004040", "404040", 
	        "200000", "002000", "000020", "202000", "200020", "002020", "202020", 
	        "600000", "006000", "000060", "606000", "600060", "006060", "606060", 
	        "A00000", "00A000", "0000A0", "A0A000", "A000A0", "00A0A0", "A0A0A0", 
	        "E00000", "00E000", "0000E0", "E0E000", "E000E0", "00E0E0", "E0E0E0", 
	    };
		Map<String, String> mapColor = new HashMap<>();
		Map<String, Map<Integer, List<String>>> maptimeanalysisref = new HashMap<>();
		Map<String, List<String>> maptimeanalysis = new HashMap<>();
		Map<String, List<String>> maptimecluster = new HashMap<>();
		Map<ResourceToShiftNumb, List<EventWorkInfo>> me = getOriginatorTimeAnalysis(context, config);
		double[][] datas = new double[me.size()][1];
		String[] mapdatas = new String[me.size()];
		UnitDist[] arUd = new UnitDist[config.getPropTimeAnalysis().length];
		boolean arUdNotSet = true;
		
		double maxDur = 0, minDur = Double.MAX_VALUE;
		int idxnontime = -1;
		
		int i = 0;
		try {
			for(Entry<ResourceToShiftNumb,List<EventWorkInfo>> es : me.entrySet())
			{ 
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String key1 = es.getKey().getResource();
				int key2 = es.getKey().getShiftnumb();
				String start = es.getValue().get(0).getStarttime();
				String end = es.getValue().get(es.getValue().size()-1).getStarttime();
				
				if(icol == colValues.length)
					icol = 0;
				
				String colorBin = mapColor.getOrDefault(key1, "#"+colValues[icol++]);
				mapColor.put(key1, colorBin);
				
				Date ds = formatter.parse(start);
				Date de = formatter.parse(end);
				Duration duration = Duration.between(formatter.parse(start).toInstant(), formatter.parse(end).toInstant());

				Map<Integer, List<String>> map1 = maptimeanalysisref.getOrDefault(key1, new HashMap<Integer, List<String>>());
				List<String> listofwork = map1.getOrDefault(key1, new ArrayList<>());
				listofwork.add(start);
				listofwork.add(end);
				long mintotal = duration.toMinutes();
				listofwork.add(Math.floor(mintotal/60)+"");
				listofwork.add((mintotal % 60)+"");
				listofwork.add(colorBin);
				map1.put(key2, listofwork);
				maptimeanalysisref.put(key1, map1);
				maptimeanalysis.put(key1+":"+key2, listofwork);
				double[] features = new double[config.getPropTimeAnalysis().length];//{mintotal, ds.getHours(), de.getHours()};
				int idx = 0;
				
				for(String prop : config.getPropTimeAnalysis()) {
					if(arUdNotSet) {
						if(prop.equals("ss") || prop.equals("es"))
							arUd[idx] = UnitDist.Time;
						else {
							arUd[idx] = UnitDist.NonTime;
							idxnontime = idx;
						}
					}
					double durInHour = mintotal/60d;
					features[idx++] = prop.equals("ss") ? ds.getHours() : (prop.equals("es") ? de.getHours() : durInHour);
					
					if(durInHour > maxDur)
						maxDur = durInHour;
					if(durInHour < minDur)
						minDur = durInHour;
				}
				arUdNotSet = false;
				
				datas[i] = features;
				mapdatas[i] = key1+":"+key2;
				i++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		//int numb = (int) Math.ceil(Math.sqrt(5*Math.sqrt(datas.length)));
		//MyOwnSOM cluster = new MyOwnSOM(datas, numb, numb, 500, 0.5, TimeUnit.Hour, arUd);
        
		//K-Means with automatically k decision
		//We can let k is growing always and decide which one is the best one but it may cause time computation very long (depend on number of data and number of attribute as well)
		//Right now we limit k to 5
		//Based on the experiment, for this problem automatic K-Means clustering perform better than SOM
		
		if(idxnontime != -1)
		for(int cc=0; cc<datas.length; cc++) {
			double[] feature = datas[cc];
			feature[idxnontime] = 12*(feature[idxnontime] - minDur)/(maxDur - minDur);
			datas[cc] = feature;
		}
		
		System.out.println("Just in");
		MyKMeans cluster = new MyKMeans(datas, TimeUnit.Hour, arUd, 500);
		int k = 1;
		int kAtElbow = 1;
		boolean isRun = true;
		while(isRun) {
			cluster.init(k);
			cluster.train();
			cluster.ssePerK.add(cluster.calcSSETotal());
			
			double bestSdptl = 0;
			int cBetter = 0;
			for(int kr = 1; kr <= cluster.ssePerK.size(); kr++) {
				double sdptl = cluster.shortestDistancePointToLine(kr, 
				  cluster.ssePerK.get(kr-1),1, 
				  cluster.ssePerK.get(0), 
				  cluster.ssePerK.size(), 
				  cluster.ssePerK.get(cluster.ssePerK.size()-1));
				
				if(sdptl > bestSdptl) {
					bestSdptl = sdptl;
					cBetter = 0;
					kAtElbow = kr;
				}
				else
					cBetter++;
				
				if(cBetter > 5) {
					isRun = false;
					break;
				}
			}
			System.out.println("K now "+k);
			if(k == 6)
				isRun = false;
			k++;
		}
		
		cluster.init(kAtElbow);
		cluster.train();
        int[] nodes = cluster.getNode();
        
        i = 0;
        for (int node : nodes) {
        	List<String> list = maptimecluster.getOrDefault(node+"", new ArrayList<>());
        	list.add(mapdatas[i]);
            maptimecluster.put(node+"", list);
            i++;
        }
        System.out.println("Finish automatic clustering");
        System.out.println("Best k is "+kAtElbow);
        result.setTimecluster(maptimecluster);
		result.setTimeanalysis(maptimeanalysis);
	}
	
	public ResourceMinerResult getResult() {
		return result;
	}
	
	public Map<ResourceToShiftNumb, List<EventWorkInfo>> getOriginatorTimeAnalysis(ServiceContext context, ResourceMinerConfig config) {
		Map<String, Integer> maporishiftnumb = new LinkedHashMap<>();
		Map<ResourceToShiftNumb, List<EventWorkInfo>> oriTimeAnalysis = new LinkedHashMap<>();
		try (Connection conn = context.getDataSource().getConnection();) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT starttime, comptimeprev, resource, gapmin, \n")
				.append("TO_CHAR(gapmin::interval, 'HH24:MI') as gapdt \n")
				.append("from (\n")
				.append("  select starttime, comptimeprev, resource, \n")
			    .append("  case when gap is not null then concat(gap,' minute') end as gapmin \n")
			    .append("  from (\n")
			    .append("    select starttime, comptimeprev, resource, \n")
			    .append("    DATE_PART('day', starttime::timestamp - comptimeprev::timestamp)*24*60 + \n")
				.append("    DATE_PART('hour', starttime::timestamp - comptimeprev::timestamp)*60 + \n")
				.append("    DATE_PART('min', starttime::timestamp - comptimeprev::timestamp) as gap \n")
				.append("    from (\n")
				.append("      select ").append(config.getColEventTimestampStart()).append(" as starttime, \n")
				.append("      case \n")
				.append("        when (lag( ").append(config.getColEventResource()).append(" ) over (order by ").append(config.getColEventResource()).append(", ").append(config.getColEventTimestampStart()).append(" )) = ").append(config.getColEventResource()).append(" then \n") 
			    .append("          (lag( ").append(config.getColEventTimestampStart()).append(" ) over (order by ").append(config.getColEventResource()).append(" , ").append(config.getColEventTimestampStart()).append(" )) \n") 
			    .append("        else null \n")
			    .append("      end as comptimeprev , \n")
			    .append("   ").append(config.getColEventResource()).append(" as resource \n") 
				.append("      from ").append(config.getDatasetId()).append(" where ").append(config.getColEventResource()).append(" != '' and c2 >= '2018-12-15'\n")//append(" != '' \n")
				.append("      ) as temp1tab \n")
				.append("    ) as temp2tab \n")
				.append("  ) fintab");
			System.out.println(sql.toString());
			ResourceToShiftNumb rsn = null;
			try (PreparedStatement st = conn.prepareStatement(sql.toString());
				ResultSet rs = st.executeQuery();) {
				while (rs.next()) {
					String starttime = rs.getString(1);
					String comptimeprev = rs.getString(2);
					String resource = rs.getString(3);
					String gapmin = rs.getString(4);
					String gapdt = rs.getString(5);
					
					EventWorkInfo work = new EventWorkInfo();
					work.setStarttime(starttime);
					work.setComptimeprev(comptimeprev);
					work.setGapmin(gapmin);
					work.setGapdt(gapdt);
					
					if(rsn == null || gapdt == null || Integer.parseInt(gapdt.split(":")[0]) >= 8)
					{
						int newShiftNumb = maporishiftnumb.getOrDefault(resource, 0) + 1;
						rsn = new ResourceToShiftNumb(resource, newShiftNumb);
						maporishiftnumb.put(resource, newShiftNumb);
					}
					
					List<EventWorkInfo> listofwork = oriTimeAnalysis.getOrDefault(rsn, new ArrayList<>());
					listofwork.add(work);
					oriTimeAnalysis.put(rsn, listofwork);
				}
			}
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
		return oriTimeAnalysis;
	}
	
	public Map<String, List<String>> reverseMapping(Map<String, List<String>> map, List<String> newKeys) {
		Map<String, List<String>> newMap = new HashMap<>();
		//check if null, if not null remove duplicate values first then transform back to List
		newKeys = newKeys != null ? newKeys : new ArrayList<>(new HashSet<>(map.values().stream().flatMap(List::stream).collect(Collectors.toList())));
		for(String key : newKeys) {
			List<String> values = new ArrayList<>();
			for(Entry<String, List<String>> es : map.entrySet()) {
				if(es.getValue().contains(key))
					values.add(es.getKey());
			}
			newMap.put(key, values);
		}
		return newMap;
	}
}
