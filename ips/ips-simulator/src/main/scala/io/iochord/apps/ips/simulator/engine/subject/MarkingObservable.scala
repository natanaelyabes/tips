package io.iochord.apps.ips.simulator.engine.subject

import java.util.Observer
import java.util.Observable
import scala.collection.mutable._

class MarkingObservable extends Observable {
  
  def setMarking(marking:(Map[(String,Map[String,String]),Any],Map[(String,Map[String,String]),Any],String,Map[(String,String),Int],Map[(String,String,Int),Double],Map[(String,String,Int),Double],Long)) {
    notifyObservers(marking)
    this.setChanged()
  }
}