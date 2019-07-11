package io.iochord.dev.chdsr.simulator;

import java.util.Observable;
import java.util.Observer;

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
		simulation.runStepSimulation(5);
		simulation.runStepSimulation(10);
		simulation.runStepSimulation(5);
	}
}
