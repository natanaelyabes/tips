package io.iochord.dev.chdsr.simulator.compiler.test

import io.iochord.dev.chdsr.simulator.engine.observer.MarkingObserver
import io.iochord.dev.chdsr.model.cpn.v1.impl.Place
import io.iochord.dev.chdsr.simulator.compiler.MemoryScalaFileCompiler
import org.json.JSONArray
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer

class SimulatorPerformAnalysis {
  
  def doATMTestWithManyToken(noStep:Int,jsonStr:String):String = {
    val map = HashMap[String,Any]()
    
    var placesbef = List[Map[String,Any]]()
    var placesaft = List[Map[String,Any]]()
    var memorybef = HashMap[String,String]()
    var memoryaft = HashMap[String,String]()
    
    val jarr = new JSONArray(jsonStr);
    
    val mb = (1024*1024).toFloat
    
		val runtime1 = Runtime.getRuntime
    val used1 = (runtime1.totalMemory - runtime1.freeMemory) / mb
    val free1 = runtime1.freeMemory() / mb
    val total1 = runtime1.totalMemory() / mb
    val max1 = runtime1.maxMemory() / mb
    
    memorybef += ("used" -> (used1 + " mb") )
    memorybef += ("free" -> (free1 + " mb") )
    memorybef += ("total" -> (total1 + " mb") )
    memorybef += ("max" -> (max1 + " mb") )
    
    map += ("memorybef" -> memorybef.toMap)
    
    val pathfile = "../chdsr-simulator/simulscala.txt"
    val memoryScalaFactory = new MemoryScalaFileCompiler(pathfile)
    val simulation = memoryScalaFactory.getInstance
    simulation.addObserver(new MarkingObserver())
    
		val cgraph = simulation.getPetriNet();
		/*
    cgraph.allTransitions.foreach(t => {
			println(t.getId()+" - "+t.getOrigin());
		})
		*/
    
    //places before
		cgraph.allPlaces.foreach(p => {
		  val pc = JsonConverter.buildPlace(jarr,p)
			placesbef = pc.toMap :: placesbef
		})
		
		map += ("placebef" -> placesbef)
		
		val start = System.currentTimeMillis()
		simulation.runStep(noStep)
		val processTime = System.currentTimeMillis() - start
		map += ("elapstime" -> (processTime + " milisec") )
		
		//places after
		cgraph.allPlaces.foreach(p => {
		  val pc = JsonConverter.buildPlace(jarr,p)
			placesaft = pc.toMap :: placesaft
		})
		
		map += ("placeaft" -> placesaft)
    
		val runtime2 = Runtime.getRuntime
    val used2 = (runtime1.totalMemory - runtime1.freeMemory) / mb
    val free2 = runtime1.freeMemory() / mb
    val total2 = runtime1.totalMemory() / mb
    val max2 = runtime1.maxMemory() / mb
    
    memoryaft += ("used" -> (used2 + " mb") )
    memoryaft += ("free" -> (free2 + " mb") )
    memoryaft += ("total" -> (total2 + " mb") )
    memoryaft += ("max" -> (max2 + " mb") )
    memoryaft += ("dused" -> ( (used2 - used1) + " mb") )
    
    map += ("memoryaft" -> memoryaft.toMap)
    
		println(map.toMap.toString())
		
	  JsonConverter.toJson(map.toMap)
  }
  
  def doBigModelTestWithManyToken(noStep:Int,jsonStr:String):String = {
    val map = HashMap[String,Any]()
    
    var placesbef = List[Map[String,Any]]()
    var placesaft = List[Map[String,Any]]()
    var memorybef = HashMap[String,String]()
    var memoryaft = HashMap[String,String]()
    
    val jarr = new JSONArray(jsonStr);
    
    val mb = (1024*1024).toFloat
    
		val runtime1 = Runtime.getRuntime
    val used1 = (runtime1.totalMemory - runtime1.freeMemory) / mb
    val free1 = runtime1.freeMemory() / mb
    val total1 = runtime1.totalMemory() / mb
    val max1 = runtime1.maxMemory() / mb
    
    memorybef += ("used" -> (used1 + " mb") )
    memorybef += ("free" -> (free1 + " mb") )
    memorybef += ("total" -> (total1 + " mb") )
    memorybef += ("max" -> (max1 + " mb") )
    
    map += ("memorybef" -> memorybef.toMap)
    
    val pathfile = "../chdsr-simulator/bigsimulscala.txt"
    val memoryScalaFactory = new MemoryScalaFileCompiler(pathfile)
    val simulation = memoryScalaFactory.getInstance
    simulation.addObserver(new MarkingObserver())
    
		val cgraph = simulation.getPetriNet();
		/*
    cgraph.allTransitions.foreach(t => {
			println(t.getId()+" - "+t.getOrigin());
		})
		*/
    
    //places before
		cgraph.allPlaces.foreach(p => {
		  val pc = JsonConverter.buildPlace(jarr,p)
			placesbef = pc.toMap :: placesbef
		})
		
		map += ("placebef" -> placesbef)
		
		val start = System.currentTimeMillis()
		simulation.runStep(noStep)
		val processTime = System.currentTimeMillis() - start
		map += ("elapstime" -> (processTime + " milisec") )
		
		//places after
		cgraph.allPlaces.foreach(p => {
		  val pc = JsonConverter.buildPlace(jarr,p)
			placesaft = pc.toMap :: placesaft
		})
		
		map += ("placeaft" -> placesaft)
    
		val runtime2 = Runtime.getRuntime
    val used2 = (runtime1.totalMemory - runtime1.freeMemory) / mb
    val free2 = runtime1.freeMemory() / mb
    val total2 = runtime1.totalMemory() / mb
    val max2 = runtime1.maxMemory() / mb
    
    memoryaft += ("used" -> (used2 + " mb") )
    memoryaft += ("free" -> (free2 + " mb") )
    memoryaft += ("total" -> (total2 + " mb") )
    memoryaft += ("max" -> (max2 + " mb") )
    memoryaft += ("dused" -> ( (used2 - used1) + " mb") )
    
    map += ("memoryaft" -> memoryaft.toMap)
    
		println(map.toMap.toString())
		
	  JsonConverter.toJson(map.toMap)
  }
}

object JsonConverter {
  def toJson(o: Any) : String = {
    var json = new ListBuffer[String]()
    o match {
      case m: Map[_,_] => {
        for ( (k,v) <- m ) {
          var key = escape(k.asInstanceOf[String])
          v match {
            case a: Map[_,_] => json += "\"" + key + "\":" + toJson(a)
            case a: List[_] => json += "\"" + key + "\":" + toJson(a)
            case a: Int => json += "\"" + key + "\":" + a
            case a: Long => json += "\"" + key + "\":" + a
            case a: Boolean => json += "\"" + key + "\":" + a
            case a: String => json += "\"" + key + "\":\"" + escape(a) + "\""
            case _ => ;
          }
        }
      }
      case m: List[_] => {
        var list = new ListBuffer[String]()
        for ( el <- m ) {
          el match {
            case a: Map[_,_] => list += toJson(a)
            case a: List[_] => list += toJson(a)
            case a: Int => list += a.toString()
            case a: Long => list += a.toString()
            case a: Boolean => list += a.toString()
            case a: String => list += "\"" + escape(a) + "\""
            case _ => ;
          }
        }
        return "[" + list.mkString(",") + "]"
      }
      case _ => ;
    }
    return "{" + json.mkString(",") + "}"
  }

  private def escape(s: String) : String = {
    return s.replaceAll("\"" , "\\\\\"");
  }
  
  def buildPlace(jarr:JSONArray, p:Place[_]):HashMap[String,Any] = {
    val pc = HashMap[String,Any]()
		  
		pc += ("id" -> p.getId())
		pc += ("origin" -> p.getOrigin().keySet.head)
		pc += ("name" -> p.getName())
		
		for(i <- 0 to jarr.length()-1) {
		  val job = jarr.getJSONObject(i)
		  if(job.getString("id").equals(p.getId())) {
		    val noInitToken = job.getInt("numInitToken")
		    for(j <- 1 to noInitToken)
		      p.addTokenWithTime( ((j,"0-generator-1"),0L), 1 )
		  }
		}
		
		pc += ("marking" -> p.getCurrentMarking().getMap())
  }
}