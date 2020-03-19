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
 * Example observer class to monitoring simulation result
 *
 */

class MarkingObserver extends Observer {
  
  /**
   * param o
   * param marking
   */
  def update(o:Observable, marking:Object):Unit =
  {
    val m = marking.asInstanceOf[(Long, Int, String, Map[(String,Map[String,String]),Any],Map[(String,Map[String,String]),Any])]
    println("JALAN", m)
  }
}