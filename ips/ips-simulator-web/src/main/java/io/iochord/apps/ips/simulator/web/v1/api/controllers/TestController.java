package io.iochord.apps.ips.simulator.web.v1.api.controllers;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.apps.ips.model.converter.sbp2cpn.Sbpnet2CpnscalaBiConverter;
import io.iochord.apps.ips.model.example.SbpnetExample;
import io.iochord.apps.ips.model.ism.v1.Ism;
import io.iochord.apps.ips.simulator.compiler.MemoryScalaCompiler;
import io.iochord.apps.ips.simulator.compiler.Simulation;
import io.iochord.apps.ips.simulator.web.TestService;
import io.iochord.apps.ips.util.SerializationUtil;
import lombok.Getter;
import scala.Tuple3;
import scala.collection.Iterator;
import scala.collection.mutable.HashMap;

/**
 *
 * @package chdsr-simulator-web
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
@RestController
@CrossOrigin
public class TestController extends AServiceController {
	public static final String BASE_URI = AServiceController.BASE_URI + "/test";
	
	@Getter
	private Ism snet;
	
	@Autowired
	private TestService svc;
	
	@RequestMapping(value=BASE_URI + "/async")
	public Long getAsync() {
		svc.asyncService();
		return 1l;
	}

	@RequestMapping(value=BASE_URI + "/model",produces= {MediaType.APPLICATION_JSON_VALUE})
	public String get01CreateExampleSimulationModel() {
		snet = SbpnetExample.createComplete();
		return SerializationUtil.encode(snet);
	}
	
	@Getter
	private String conversionResult;

	@RequestMapping(value=BASE_URI + "/convert",produces= {MediaType.APPLICATION_JSON_VALUE})
	public String get02ConvertToCPNScala() {
		if (snet == null) {
			get01CreateExampleSimulationModel();
		}
		Sbpnet2CpnscalaBiConverter converter = new Sbpnet2CpnscalaBiConverter();
		conversionResult = converter.convert(snet);
		return conversionResult;
	}
	
	@Getter
	private Simulation simulationInstance;

	@Getter
	java.util.Map<String, String> reverseLookup = new java.util.LinkedHashMap<>();

	@Getter
	java.util.Map<String, Integer> basicMonitorResult = new java.util.LinkedHashMap<>();
	
	
	@RequestMapping(value=BASE_URI + "/result",produces= {MediaType.APPLICATION_JSON_VALUE})
	public java.util.Map<String, Integer> getResult() {
		return basicMonitorResult;
	}
	
	@Getter
	private Queue<String> outputMessage = new PriorityBlockingQueue<>();

	@RequestMapping(value=BASE_URI + "/init",produces= {MediaType.APPLICATION_JSON_VALUE})
	public Map<String, Integer> get03InitSimulation() {
		if (conversionResult == null) {
			get02ConvertToCPNScala();
		}
		MemoryScalaCompiler msfc = new MemoryScalaCompiler(conversionResult);
		simulationInstance = msfc.getInstance();
		Observer obs = new Observer() {
			
			@SuppressWarnings({ "unused", "unchecked" })
			@Override
			public void update(Observable o, Object arg) {
				System.out.println("JAVAOBS: " + o);
				System.out.println(arg);
				Tuple3<?, ?, ?> t = (Tuple3<?, ?, ?>) arg;
				HashMap<?, ?> prev = (HashMap<?, ?>) t._1();
				HashMap<Object, Object> next = (HashMap<Object, Object>) t._2();
				Iterator<Object> i = next.keysIterator();
				while (i.hasNext()) {
					Object k = i.next();
					HashMap<Object, Integer> v = (HashMap<Object, Integer>) next.get(k).get();
					String ks = k.toString();
					if (reverseLookup.containsKey(ks)) {
						String ename = reverseLookup.get(ks);
						int vit = 0;
						if (v != null) {
							Iterator<Integer> vi = v.valuesIterator(); 
							while (vi.hasNext()) {
								vit += vi.next();
							}
						}
						basicMonitorResult.put(ename, vit);
					}
				}
				outputMessage.add(o.toString() + ": " +  arg.toString());
			}
			
		};
		simulationInstance.addObserver(obs);
//		for (Element e : conversionResult.getBasicMonitors().keySet()) {
//			Pair<String, String> ev = conversionResult.getBasicMonitors().get(e);
//			String ename = e.getId() + " --> " + ev.getFirst() + " = " + ev.getSecond();
//			reverseLookup.put(ev.getFirst(), ename);
//			basicMonitorResult.put(ename, 0);
//		}
		return basicMonitorResult;
	}
	
	@Getter
	private boolean isRunning = false;

	@RequestMapping(value=BASE_URI + "/run",produces= {MediaType.APPLICATION_JSON_VALUE})
	public Queue<String> get04Step() {
		if (simulationInstance == null) {
			get03InitSimulation();
		}
//		getSimulationInstance().runSimulation();
		return outputMessage;
	}

}
