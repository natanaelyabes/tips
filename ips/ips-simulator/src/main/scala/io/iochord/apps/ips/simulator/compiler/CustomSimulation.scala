package io.iochord.apps.ips.simulator.compiler

import io.iochord.apps.ips.simulator.engine.observer.MarkingObserver
import org.json.JSONObject
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import org.json.JSONArray
import io.iochord.apps.ips.model.cpn.v1.impl.Place

/**
 *
 * @package ips-simulator
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */
class CustomSimulation {

  def doTestWithManyToken(noStep: Int, jsonStr: String, modelpath: String): String = {
    val map = HashMap[String, Any]()

    var placesbef = List[Map[String, Any]]()
    var placesaft = List[Map[String, Any]]()

    val job = new JSONObject(jsonStr);

    var mpath = modelpath;

    if (mpath == null)
      mpath = job.getString("mpath");

    val startcomp = System.currentTimeMillis()

    val memoryScalaFactory = new MemoryScalaFileCompiler(mpath)
    val simulation = memoryScalaFactory.getInstance
    //simulation.addObserver(new MarkingObserver())

    val compTime = System.currentTimeMillis() - startcomp
    map += ("compiletime" -> (compTime + " milisec"))

    val cgraph = simulation.getPetriNet()

    //places before
    cgraph.allPlaces.foreach(p => {
      val pc = JsonConverter.buildPlace(job.getJSONArray("addInitToken"), p, true, job.getBoolean("showPlaces"))
      placesbef = pc.toMap :: placesbef
    })

    map += ("placebef" -> placesbef)

    val start = System.currentTimeMillis()

    if (job.getBoolean("doConcurrent"))
      simulation.runStepWithCon(noStep)
    else
      simulation.runStep(noStep)

    val simulTime = System.currentTimeMillis() - start

    map += ("simultime" -> (simulTime + " milisec"))
    map += ("avgentrtime" -> (simulation.getAvgEnTrTime() + " nanosec"))

    map += ("stopStep" -> simulation.getCurrentStep())

    //places after
    cgraph.allPlaces.foreach(p => {
      val pc = JsonConverter.buildPlace(job.getJSONArray("addInitToken"), p, false, job.getBoolean("showPlaces"))
      placesaft = pc.toMap :: placesaft
    })

    map += ("placeaft" -> placesaft)

    JsonConverter.toJson(map.toMap)
  }
}

object JsonConverter {
  def toJson(o: Any): String = {
    var json = new ListBuffer[String]()
    o match {
      case m: Map[_, _] => {
        for ((k, v) <- m) {
          var key = escape(k.asInstanceOf[String])
          v match {
            case a: Map[_, _] => json += "\"" + key + "\":" + toJson(a)
            case a: List[_]   => json += "\"" + key + "\":" + toJson(a)
            case a: Int       => json += "\"" + key + "\":" + a
            case a: Long      => json += "\"" + key + "\":" + a
            case a: Boolean   => json += "\"" + key + "\":" + a
            case a: String    => json += "\"" + key + "\":\"" + escape(a) + "\""
            case _            => ;
          }
        }
      }
      case m: List[_] => {
        var list = new ListBuffer[String]()
        for (el <- m) {
          el match {
            case a: Map[_, _] => list += toJson(a)
            case a: List[_]   => list += toJson(a)
            case a: Int       => list += a.toString()
            case a: Long      => list += a.toString()
            case a: Boolean   => list += a.toString()
            case a: String    => list += "\"" + escape(a) + "\""
            case _            => ;
          }
        }
        return "[" + list.mkString(",") + "]"
      }
      case _ => ;
    }
    return "{" + json.mkString(",") + "}"
  }

  private def escape(s: String): String = {
    return s.replaceAll("\"", "\\\\\"");
  }

  def buildPlace(jarr: JSONArray, p: Place[_], isBefore: Boolean, isShowPlaces: Boolean): HashMap[String, Any] = {
    if (isBefore)
      for (i <- 0 to jarr.length() - 1) {
        val job = jarr.getJSONObject(i)
        if (job.getString("id").equals(p.getId())) {
          val noInitToken = job.getInt("addToken")
          for (j <- 1 to noInitToken)
            p.addTokenWithTime(((j, "0-generator-1"), 0L), 1)
        }
      }

    val pc = HashMap[String, Any]()

    if (isShowPlaces) {
      pc += ("id" -> p.getId())
      pc += ("origin" -> p.getOrigin().keySet.head)
      pc += ("name" -> p.getName())

      pc += ("marking" -> p.getCurrentMarking().getMap().toString())
    }

    pc
  }
}