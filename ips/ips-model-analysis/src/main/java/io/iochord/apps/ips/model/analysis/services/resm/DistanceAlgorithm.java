package io.iochord.apps.ips.model.analysis.services.resm;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DistanceAlgorithm {
	
	public static final String METHOD_PCC = "pcc";
	public static final String METHOD_HAM = "ham";
	public static final String METHOD_H01 = "h01";
	
	public Double calcDist(String distMethod, String resource1, String resource2, List<String> activities, Map<ResourceToActivity, Integer> oacmtrx) {
		if(distMethod.equals(METHOD_PCC))
			return calcDistPCC(resource1, resource2, activities, oacmtrx);
		else if(distMethod.equals(METHOD_HAM))
			return calcDistHamming(resource1, resource2, activities, oacmtrx);
		else
			return calcDistHammingOneZero(resource1, resource2, activities, oacmtrx);
	}
	
	private Double calcDistPCC(String resource1, String resource2, List<String> activities, Map<ResourceToActivity, Integer> oacmtrx) {
		int sumResource1 = 0;
		int sumResource2 = 0;
		for(String activity : activities) {
			Optional<ResourceToActivity> orta1 = oacmtrx.keySet().stream().filter(o -> (o.getResource().equals(resource1) && o.getActivity().equals(activity))).findFirst();
			Optional<ResourceToActivity> orta2 = oacmtrx.keySet().stream().filter(o -> (o.getResource().equals(resource2) && o.getActivity().equals(activity))).findFirst();
			int freq1 = oacmtrx.getOrDefault(orta1.isPresent() ? orta1.get() : null,0);
			int freq2 = oacmtrx.getOrDefault(orta2.isPresent() ? orta2.get() : null,0);
			
			sumResource1 += freq1;
			sumResource2 += freq2;
		}
		double meanResource1 = (double)sumResource1/activities.size();
		double meanResource2 = (double)sumResource2/activities.size();
		
		double sumEq1 = 0;
		double sumEq2 = 0;
		double sumEq3 = 0;
		for(String activity : activities) {
			Optional<ResourceToActivity> orta1 = oacmtrx.keySet().stream().filter(o -> (o.getResource().equals(resource1) && o.getActivity().equals(activity))).findFirst();
			Optional<ResourceToActivity> orta2 = oacmtrx.keySet().stream().filter(o -> (o.getResource().equals(resource2) && o.getActivity().equals(activity))).findFirst();
			int freq1 = oacmtrx.getOrDefault(orta1.isPresent() ? orta1.get() : null,0);
			int freq2 = oacmtrx.getOrDefault(orta2.isPresent() ? orta2.get() : null,0);
			
			double eq1 = (freq1-meanResource1)*(freq2-meanResource2);
			double eq2 = Math.pow(freq1-meanResource1,2);
			double eq3 = Math.pow(freq2-meanResource2,2);
			
			sumEq1 += eq1;
			sumEq2 += eq2;
			sumEq3 += eq3;
		}
		
		return sumEq1/Math.sqrt(sumEq2*sumEq3);
	}
	
	private Double calcDistHamming(String resource1, String resource2, List<String> activities, Map<ResourceToActivity, Integer> oacmtrx) {
		int sumDist = 0;
		for(String activity : activities) {
			Optional<ResourceToActivity> orta1 = oacmtrx.keySet().stream().filter(o -> (o.getResource().equals(resource1) && o.getActivity().equals(activity))).findFirst();
			Optional<ResourceToActivity> orta2 = oacmtrx.keySet().stream().filter(o -> (o.getResource().equals(resource2) && o.getActivity().equals(activity))).findFirst();
			int freq1 = oacmtrx.getOrDefault(orta1.isPresent() ? orta1.get() : null,0);
			int freq2 = oacmtrx.getOrDefault(orta2.isPresent() ? orta2.get() : null,0);
			
			if(freq1 != freq2)
				sumDist += Math.abs(freq1 - freq2);
		}
		
		return (double)sumDist/activities.size();
	}
	
	private Double calcDistHammingOneZero(String resource1, String resource2, List<String> activities, Map<ResourceToActivity, Integer> oacmtrx) {
		int sumDist = 0;
		for(String activity : activities) {
			Optional<ResourceToActivity> orta1 = oacmtrx.keySet().stream().filter(o -> (o.getResource().equals(resource1) && o.getActivity().equals(activity))).findFirst();
			Optional<ResourceToActivity> orta2 = oacmtrx.keySet().stream().filter(o -> (o.getResource().equals(resource2) && o.getActivity().equals(activity))).findFirst();
			int freq1 = oacmtrx.getOrDefault(orta1.isPresent() ? orta1.get() : null,0);
			int freq2 = oacmtrx.getOrDefault(orta2.isPresent() ? orta2.get() : null,0);
			
			if((freq1 > 0) ^ (freq2 > 0))
				sumDist++;
		}
		
		return (double)sumDist/activities.size();
	}
	
	public boolean evalThreshold(double value, double threshold, String method) {
		if(method.equals(METHOD_PCC))
			return value >= threshold;
		else
			return value <= threshold;
	}
}
