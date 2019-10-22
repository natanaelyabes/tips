package io.iochord.apps.ips.simulator.engine.observer

import java.util.Observer
import java.util.Observable

import scala.collection.mutable._

class MarkingObserver extends Observer {
  
  def update(o:Observable, marking:Object):Unit =
  {
    val m = marking.asInstanceOf[(Map[(Boolean,Boolean,Map[String,String]),Any],Map[(Boolean,Boolean,Map[String,String]),Any],String,Map[(String,String),Int],Map[(String,String,Int),Double],Map[(String,String,Int),Double],Long)]
    
    m._2.filter(p => p._1._1).foreach(p => { 
      println("Token Start In "+p._1._3.keys.head+" : "+p._2)
      //val nt = p._2.keySet.filterNot(m2.keySet)
      m._5 += (("","",1) -> 1)
    })
      
    m._2.filter(p => p._1._2).foreach(p => { 
      println("Token Start Out "+p._1._3.keys.head+" : "+p._2)
    })
    //println(m._4+" - "+m._3+"\n"+m._1+"\n"+m._2)  
  }
}