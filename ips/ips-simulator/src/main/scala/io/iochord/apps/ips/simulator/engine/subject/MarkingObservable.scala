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
 * Subject class that extends Observable java util for observer pattern
 *
 */

class MarkingObservable extends Observable {
  
  /**
   * @param marking : bundle of marking that is send to observer object that listen to this event 
   */
  def setMarking(marking:(Long, Int, (String,Map[String,String]), Map[(String,Map[String,String]),Any],Map[(String,Map[String,String]),Any])) {
    this.setChanged()
    notifyObservers(marking)
  }
}