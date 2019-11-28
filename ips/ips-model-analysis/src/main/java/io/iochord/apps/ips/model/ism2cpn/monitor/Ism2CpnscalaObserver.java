package io.iochord.apps.ips.model.ism2cpn.monitor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.iochord.apps.ips.common.util.JsonDataCodec;
import io.iochord.apps.ips.model.ism.v1.Element;
import io.iochord.apps.ips.model.ism.v1.data.Generator;
import io.iochord.apps.ips.model.ism.v1.nodes.Activity;
import io.iochord.apps.ips.model.ism.v1.nodes.Start;
import io.iochord.apps.ips.model.ism2cpn.converter.Ism2CpnscalaModel;
import io.iochord.apps.ips.model.report.ElementStatistics;
import lombok.Getter;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple5;
import scala.collection.Iterator;
import scala.collection.mutable.HashMap;

public class Ism2CpnscalaObserver implements Observer {
	
	@Getter
	private final Ism2CpnscalaModel model;
	
	@Getter
	private final Map<Element, ElementStatistics> data = new LinkedHashMap<>();

	public Ism2CpnscalaObserver(Ism2CpnscalaModel ism2CpnscalaModel) {
		model = ism2CpnscalaModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void update(Observable o, Object arg) {
		observe(o, arg);
//		try {
//			System.out.println(JsonDataCodec.getSerializer().writeValueAsString(arg));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void observe(Observable o, Object arg) {
		Tuple5 tuple5 = (Tuple5) arg;
//		long time = (long) tuple5._1();
//		int step = (int) tuple5._2();
		Tuple2 transition = (Tuple2) tuple5._3();
//		String transitionId = (String) transition._1();
		HashMap transitionOrigin = (HashMap) transition._2();
		String transitionEleId = (String) transitionOrigin.get("origin").get();
		String transitionEleRole = (String) transitionOrigin.get("role").get();
		HashMap prevState = (HashMap) tuple5._4();
		HashMap currentState = (HashMap) tuple5._5();
		Map<String, Tuple3> prevStateRole = getStateRole(prevState);			
		Map<String, Tuple3> currentStateRole = getStateRole(currentState);
			
		Element e = getModel().getConversionMap().get(transitionEleId);
		System.out.println(transitionEleId + " " + transitionEleRole);
		if (e != null) {
			if (e instanceof Generator) {
				if (prevStateRole.get("_dgp2") != null) {
					HashMap dgp2Before = (HashMap) prevStateRole.get("_dgp2")._2();
					HashMap dgp2After = (HashMap) currentStateRole.get("_dgp2")._2();
					int newToken = dgp2After.size() - dgp2Before.size();
					if (!getData().containsKey(e)) {
						getData().put(e, new ElementStatistics(e.getLabel(), "Generator", "Instance Generated", 0l, null, null, null));
					}
					getData().get(e).setCount(getData().get(e).getCount() + newToken);
				}
			}
			if (e instanceof Activity) {
				if (transitionEleRole.equalsIgnoreCase("_natstart")) {
					if (prevStateRole.get("_resp") != null) {
						HashMap dgp2Before2 = (HashMap) prevStateRole.get("_resp")._2();
						HashMap dgp2After2 = (HashMap) currentStateRole.get("_resp")._2();
						int newToken2 = dgp2Before2.size() - dgp2After2.size();
						Element re = getModel().getConversionMap().get((String) prevStateRole.get("_resp")._3());
						if (!getData().containsKey(re)) {
							getData().put(re, new ElementStatistics(re.getLabel(), "Resource", "Instance Used", 0l, null, null, null));
						}
						getData().get(re).setCount(getData().get(re).getCount() + newToken2);
					}
				}
				if (transitionEleRole.equalsIgnoreCase("_natend")) {
					HashMap dgp2Before = (HashMap) prevStateRole.get("_nap3")._2();
					HashMap dgp2After = (HashMap) currentStateRole.get("_nap3")._2();
					int newToken = dgp2After.size() - dgp2Before.size();
					if (!getData().containsKey(e)) {
						getData().put(e, new ElementStatistics(e.getLabel(), "Activity", "Instance Processed", 0l, null, null, null));
					}
					getData().get(e).setCount(getData().get(e).getCount() + newToken);
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
	
}