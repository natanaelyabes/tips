package io.iochord.dev.chdsr.simulator;

import java.util.Observable;

import io.iochord.apps.ips.model.cpn.v1.impl.CPNGraph;
import io.iochord.apps.ips.model.cpn.v1.impl.Place;
import io.iochord.apps.ips.model.cpn.v1.impl.Transition;
import io.iochord.apps.ips.simulator.compiler.MemoryScalaFileCompiler;
import io.iochord.apps.ips.simulator.compiler.Simulation;
import scala.tools.jline_embedded.internal.Log;

/**
 *
 * @package chdsr-simulator
 * @author  Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 * @since   2019
 *
 *
 */
public class TestSimulator {
	public static void main(String[] args) {
		String pathFile = "simulscala.txt";
		MemoryScalaFileCompiler msfc = new MemoryScalaFileCompiler(pathFile);
		Simulation simulation = msfc.getInstance();
		simulation.addObserver((Observable o, Object arg) -> {
				Log.debug("JAVAOBS: " + o);
				Log.debug(arg);
			});
		
		simulation.runStep(5);
		simulation.runStep(3);
		simulation.runStep(3);
		
		CPNGraph cgraph = simulation.getPetriNet();
		for(Transition<?> t : cgraph.getTransitions()) {
			Log.debug(t.getId());
		}
		
		for(Place<?> p : cgraph.getPlaces()) {
			Log.debug(p.getId());
			Log.debug(p.getCurrentMarking().getMap());
		}
		
	}
}