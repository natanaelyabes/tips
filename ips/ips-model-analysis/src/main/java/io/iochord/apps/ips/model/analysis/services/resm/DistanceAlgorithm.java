package io.iochord.apps.ips.model.analysis.services.resm;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DistanceAlgorithm {
	
	public static Double calcDistPCC(String resource1, String resource2, List<String> activities, Map<ResourceToActivity, Integer> oacmtrx) {
		int sumResource1 = 0, sumResource2 = 0;
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
		
		double sumEq1 = 0, sumEq2 = 0, sumEq3 = 0;
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
}
