package io.iochord.apps.ips.model.ism2cpn.monitor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import io.iochord.apps.ips.common.util.LoggerUtil;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.report.ElementStatistics;
import lombok.Getter;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.collection.immutable.List;
import scala.collection.mutable.HashMap;

/**
*
* @package ips-model-analysis
* @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
* @since 2019
*
*/
public class Ism2CpnscalaObserver implements Observer {
	
	@Getter
	Map<String, Element> conversionMap;
	
	@Getter
	private final Map<Element, ElementStatistics> data = new LinkedHashMap<>();

	public Ism2CpnscalaObserver(Map<String, Element> conversionMap) {
		this.conversionMap = conversionMap;
	}

	@Override
	public void update(Observable o, Object arg) {
		observe(o, arg);
		try {
//			System.out.println(JsonDataCodec.getSerializer().writeValueAsString(arg));
		} catch (Exception ex) {
			LoggerUtil.logError(ex);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void observe(Observable o, Object arg) {
		Tuple5 tuple5 = (Tuple5) arg;
//		long globalClock = (long) tuple5._1();
//		int globalStep = (int) tuple5._2();
		Tuple2 transition = (Tuple2) tuple5._3();
//		String transitionId = (String) transition._1();
		HashMap transitionOrigin = (HashMap) transition._2();
		String transitionEleId = (String) transitionOrigin.get("origin").get();
		String transitionEleRole = (String) transitionOrigin.get("role").get();
		HashMap prevState = (HashMap) tuple5._4();
		HashMap currentState = (HashMap) tuple5._5();
		Map<String, Tuple3> prevStateRole = getStateRole(prevState);			
		Map<String, Tuple3> currentStateRole = getStateRole(currentState);
			
		Element e = getConversionMap().get(transitionEleId);
		// LoggerUtil.logInfo(transitionEleId + " " + transitionEleRole);
		if (e != null) {
			if (e instanceof Generator) {
				String dgp2Str = "_dgp2";
				if (prevStateRole.get(dgp2Str) != null) {
					HashMap dgp2Before = (HashMap) prevStateRole.get(dgp2Str)._2();
					HashMap dgp2After = (HashMap) currentStateRole.get(dgp2Str)._2();
					int newToken = dgp2After.size() - dgp2Before.size();
					if (!getData().containsKey(e)) {
						getData().put(e, new ElementStatistics(e.getLabel(), "Generator", 
							"Instance Generated", 0l));
					}
					getData().get(e).setCount(getData().get(e).getCount() + newToken);
				}
			}
			if (e instanceof Activity) {
				if (transitionEleRole.equalsIgnoreCase("_natstart")) {
					String qpStr = "_qendp";
					if (prevStateRole.get(qpStr) != null) {
						HashMap qpBefore = (HashMap) prevStateRole.get(qpStr)._2();
						HashMap qpAfter = (HashMap) currentStateRole.get(qpStr)._2();
						Map<Integer, Integer> caseIds = new LinkedHashMap<>();
						Iterator it = qpBefore.iterator();
						while (it.hasNext()) {
							Tuple2 a = (Tuple2) it.next();
							Tuple2 el = (Tuple2) a._1();
							Integer as = (Integer) el._2();
							List c = (List) el._1();
							Iterator it2 = c.iterator();
							while (it2.hasNext()) {
								Tuple2 a2 = (Tuple2) it2.next();
								Integer caseId = (Integer) a2._1();
								caseIds.put(caseId, as);
							}
						}
						it = qpAfter.iterator();
						while (it.hasNext()) {
							Tuple2 a = (Tuple2) it.next();
							Tuple2 el = (Tuple2) a._1();
							List c = (List) el._1();
							Iterator it2 = c.iterator();
							while (it2.hasNext()) {
								Tuple2 a2 = (Tuple2) it2.next();
								Integer caseId = (Integer) a2._1();
								caseIds.remove(caseId);
							}
						}
						qpBefore.toString();
					}

					String respStr = "_resp";
					if (prevStateRole.get(respStr) != null) {
						HashMap dgp2Before2 = (HashMap) prevStateRole.get(respStr)._2();
						HashMap dgp2After2 = (HashMap) currentStateRole.get(respStr)._2();
						int newToken2 = dgp2Before2.size() - dgp2After2.size();
						Element re = getConversionMap().get((String) prevStateRole.get("_resp")._3());
						if (!getData().containsKey(re)) {
							ElementStatistics es = new ElementStatistics(re.getLabel(), "Resource");
							getData().put(re, es);
							es.getSubElements().put("1", new ElementStatistics("Resource Used"
								, 0l, null, null, null, null));
							es.getSubElements().put("2", new ElementStatistics("처리 시간 (Turnaround Time)"
								, 0l, null, null, null, null));
							es. getSubElements().put("3", new ElementStatistics("Productivity"
								, 0l, null, null, null, null));
							es.getSubElements().put("4", new ElementStatistics("Cost"
								, 0l, null, null, null, null));
						}
						getData().get(re).getSubElements().get("1").setCount(
							getData().get(re).getSubElements().get("1").getCount() + newToken2);
					}
				}
				if (transitionEleRole.equalsIgnoreCase("_natend")) {
					HashMap dgp2Before = (HashMap) prevStateRole.get("_nap3")._2();
					HashMap dgp2After = (HashMap) currentStateRole.get("_nap3")._2();
					int newToken = dgp2After.size() - dgp2Before.size();
					if (!getData().containsKey(e)) {
						ElementStatistics es = new ElementStatistics(e.getLabel(), "Activity");
						getData().put(e, es);
						es.getSubElements().put("1", new ElementStatistics("Instance Processed"
							, 0l, null, null, null, null));
						es.getSubElements().put("2", new ElementStatistics("대기 시간 (Waiting Time)"
								, 0l, null, null, null, null));
						es.getSubElements().put("3", new ElementStatistics("처리 시간 (Processing Time)"
								, 0l, null, null, null, null));
					}
					getData().get(e).getSubElements().get("1").setCount(
						getData().get(e).getSubElements().get("1").getCount() + newToken);
				}
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Tuple3> getStateRole(HashMap state) {
		Map<String, Tuple3> map = new LinkedHashMap<>();
		Iterator it = state.keysIterator();
		while (it.hasNext()) {
			Tuple2 k = (Tuple2) it.next();
			HashMap v = (HashMap) ((Some) state.get((Object) k)).get();
//			String placeId = (String) k._1();
			HashMap placeOrigin = (HashMap) k._2();
			if (placeOrigin == null) {
				continue;
			}
			Object origin = placeOrigin.get("origin");
			Object role = placeOrigin.get("role");
			String placeEleId = origin == null ? "" : (String) placeOrigin.get("origin").get();
			String placeEleRole = role == null ? "" : (String) placeOrigin.get("role").get();
			if (origin == null || role == null) {
				continue;
			}
			map.put(placeEleRole, new Tuple3(k, v, placeEleId));
		}
		return map;
	}
	
	public void reset() {
		getData().clear();
	}
	
}
