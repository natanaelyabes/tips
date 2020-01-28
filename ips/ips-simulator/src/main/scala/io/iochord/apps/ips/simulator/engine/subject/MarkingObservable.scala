package io.iochord.apps.ips.simulator.engine.subject

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

class MarkingObservable extends Observable {
  
  def setMarking(marking:(Long, Int, (String,Map[String,String]), Map[(String,Map[String,String]),Any],Map[(String,Map[String,String]),Any])) {
    this.setChanged()
    notifyObservers(marking)
  }
}