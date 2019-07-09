package io.iochord.dev.chdsr.simulator.engine.observer

import java.util.Observer
import java.util.Observable

import scala.collection.mutable._

class MarkingObserver extends Observer {
  
  def update(o:Observable, marking:Object):Unit =
  {
    val marking_ins = marking.asInstanceOf[(Map[String,String],Map[String,String],String)]
    println(marking_ins._3+"\n"+marking_ins._1+"\n"+marking_ins._2)  
  }
}