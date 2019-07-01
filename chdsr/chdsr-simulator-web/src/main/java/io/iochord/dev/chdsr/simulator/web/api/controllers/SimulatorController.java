package io.iochord.dev.chdsr.simulator.web.api.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.iochord.dev.chdsr.simulator.compiler.MemoryScalaCompiler;
import io.iochord.dev.chdsr.simulator.compiler.Simulation;

/**
 * @author Iq Reviessay Pulshashi <pulshashi@ideas.web.id>
 *
 */
// TODO: Simulator Controller
@RestController
@CrossOrigin
public class SimulatorController extends AServiceController {
	public static final String BASE_URI = AServiceController.BASE_URI + "/simulator";

	@RequestMapping(BASE_URI + "")
	public String getIndex() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/load/{cpnId}")
	public String getLoad() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/step/{cpnId}/{n}")
	public String getStep() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/warptime/{cpnId}/{t}")
	public String getWarp() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/fastforward/{cpnId}")
	public String getFastForward() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/start")
	public String start() {
		return "Ok";
	}

	@RequestMapping(BASE_URI + "/test")
	public String test() {
		String myclassSyntax = "new Simulation {\n"+
	      "val test1 = \"Simulation MODEL-0\"\n"+
	      "override def runSimulation(cpnGraph: CPNGraph):Unit ="+
	        "{ println(\"Run Simulation\") }\n"+ 
	      "override def expState():Unit ="+
	        "{ println(\"Explore State\") }\n"+ 
	      "override def calcKPI[T](kpiFunc:T):Double ="+
	        "{ println(\"Calculate KPI\"); putVar(\"test1\",test1); putVar(\"test2\",new Coba1()); return 5.0 }\n "+  
	      "def test(var1:String):Unit = "+
	        "{ println(var1) }\n"+
	      "class Coba1 { val varc1 = \"lain lagi\" }\n"+
	    "}";
			    
		MemoryScalaCompiler memoryScalaFactory = new MemoryScalaCompiler(myclassSyntax);
	    Simulation memoryScala = memoryScalaFactory.getInstance();
		memoryScala.putVar("test3","Test Response");
	    String result = (String) memoryScala.getVar("test1");
		return result;
	}
}
