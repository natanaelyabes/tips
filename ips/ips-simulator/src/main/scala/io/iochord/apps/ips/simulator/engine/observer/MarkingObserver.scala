package io.iochord.apps.ips.simulator.engine.observer

import java.util.Observer
import java.util.Observable

import scala.collection.mutable._

/**
 *
 * @package ips-simulator
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */

class MarkingObserver extends Observer {
  
  def update(o:Observable, marking:Object):Unit =
  {
    val m = marking.asInstanceOf[(Long, Int, String, Map[(String,Map[String,String]),Any],Map[(String,Map[String,String]),Any])]
    println("JALAN", m)
    
    //println("Marking Before ---- "+m._4.toSet)
    //println("Marking After ---- "+m._5.toSet)
    //val diff = m._5.toSet.filterNot(m._4.toSet)
    //println("Dff --- "+diff)
   
    /*
    m._2.filter(p => p._1._1).foreach(p => { 
      println("Token Start In "+p._1._3.keys.head+" : "+p._2)
      //val nt = p._2.keySet.filterNot(m2.keySet)
      m._5 += (("","",1) -> 1)
    })
      
    m._2.filter(p => p._1._2).foreach(p => { 
      println("Token Start Out "+p._1._3.keys.head+" : "+p._2)
    })
    //println(m._4+" - "+m._3+"\n"+m._1+"\n"+m._2)  
    */
  }
}