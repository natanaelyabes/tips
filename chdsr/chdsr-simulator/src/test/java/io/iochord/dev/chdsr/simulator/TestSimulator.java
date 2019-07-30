package io.iochord.dev.chdsr.simulator;

import java.util.Observable;
import java.util.Observer;

import io.iochord.dev.chdsr.model.cpn.v1.impl.CPNGraph;
import io.iochord.dev.chdsr.model.cpn.v1.impl.Place;
import io.iochord.dev.chdsr.model.cpn.v1.impl.Transition;
import io.iochord.dev.chdsr.simulator.compiler.MemoryScalaFileCompiler;
import io.iochord.dev.chdsr.simulator.compiler.Simulation;

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
		Observer obs = new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
				System.out.println("JAVAOBS: " + o);
				System.out.println(arg);
			}
		};
		simulation.addObserver(obs);
		
		simulation.runStep(5);
		simulation.runStep(3);
		simulation.runStep(3);
		
		CPNGraph cgraph = simulation.getPetriNet();
		for(Transition t : cgraph.getTransitions()) {
			System.out.println(t.getId());
		}
		
		for(Place p : cgraph.getPlaces()) {
			System.out.println(p.getId());
			System.out.println(p.getCurrentMarking().getMap());
		}
		
	}
}