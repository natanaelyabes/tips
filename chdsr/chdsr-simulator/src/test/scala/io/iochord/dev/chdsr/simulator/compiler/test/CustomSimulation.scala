package io.iochord.dev.chdsr.simulator.compiler.test

import io.iochord.dev.chdsr.simulator.engine.observer.MarkingObserver
import io.iochord.dev.chdsr.model.cpn.v1.impl.Place
import io.iochord.dev.chdsr.simulator.compiler.MemoryScalaFileCompiler
import org.json.JSONObject
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer

import java.lang.management.ManagementFactory

class CustomSimulation {
  
  def doTestWithManyToken(noStep:Int,jsonStr:String,modelpath:String):String = {
    val map = HashMap[String,Any]()
    
    var placesbef = List[Map[String,Any]]()
    var placesaft = List[Map[String,Any]]()
    
    val job = new JSONObject(jsonStr)
    
    val startcomp = System.currentTimeMillis()
    
    val memoryScalaFactory = new MemoryScalaFileCompiler(modelpath)
    val simulation = memoryScalaFactory.getInstance
    simulation.addObserver(new MarkingObserver())
    
    val compTime = System.currentTimeMillis() - startcomp
		map += ("compiletime" -> (compTime + " milisec") )
		
		val cgraph = simulation.getPetriNet()
    
    //places before
		cgraph.allPlaces.foreach(p => {
		  val pc = JsonConverter.buildPlace(job.getJSONArray("addInitToken"),p,true,job.getBoolean("showPlaces"))
			placesbef = pc.toMap :: placesbef
		})
		
		map += ("placebef" -> placesbef)
		
		val start = System.currentTimeMillis()
		simulation.runStep(noStep)
		val simulTime = System.currentTimeMillis() - start
		
		map += ("simultime" -> (simulTime + " milisec") )
		map += ("avgentrtime" -> (simulation.getAvgEnTrTime() + " nanosec") )
		
		map += ("stopStep" -> simulation.getCurrentStep())
		
		//places after
		cgraph.allPlaces.foreach(p => {
		  val pc = JsonConverter.buildPlace(job.getJSONArray("addInitToken"),p,false,job.getBoolean("showPlaces"))
			placesaft = pc.toMap :: placesaft
		})
		
		map += ("placeaft" -> placesaft)
		
	  JsonConverter.toJson(map.toMap)
  }
}