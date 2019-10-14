package io.iochord.apps.ips.simulator.engine.subject

import java.util.Observer
import java.util.Observable
import scala.collection.mutable._

class MarkingObservable extends Observable {
  
  def setMarking(marking:(Map[String,Any],Map[String,Any],String,Long)) {
    notifyObservers(marking)
    this.setChanged()
  }
}