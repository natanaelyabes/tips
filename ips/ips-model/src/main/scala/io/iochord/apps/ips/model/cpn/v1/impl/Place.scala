package io.iochord.apps.ips.model.cpn.v1.impl

import io.iochord.apps.ips.model.cpn.v1._

import scala.collection.generic._
import scala.collection.mutable.Map

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */

class Place[T] (
  private var id: String,
  private var name: String,
  private val initialMarking: Multiset[T],
  private val initialMap:Map[(T,Long), Int] = Map[(T,Long), Int]()) extends Element with Node {
  
  private var currentMarking = initialMarking
  
  private var origin:Map[String,String] = null
  
  var transitions:Seq[String] = Seq[String]()
  
  def setToInitialMarking() { currentMarking = new Multiset(initialMap.clone()) }
  
  /**
   * @param tokenWithTime : remove specific token with time from multiset of this place
   */
  def removeTokenWithTime(tokenWithTime: Any, noToken: Int) {currentMarking - (tokenWithTime.asInstanceOf[(T,Long)],noToken) }

  /**
   * @param tokenWithTime : add specific token with time to multiset of this place
   */
  def addTokenWithTime(tokenWithTime: Any, noToken: Int)  { currentMarking + (tokenWithTime.asInstanceOf[(T,Long)],noToken) }
  
  /**
   * @return current marking of multiset of this place
   */
  def getCurrentMarking():Multiset[T] = currentMarking
  
  /**
   * @param cm : assign new marking for multiset of this place
   */
  def setCurrentMarking(cm:Multiset[T]) { currentMarking = cm }
  
  /**
   * @return id of this place. (see trait Element for detail explanation)
   */
  def getId(): String = id
  
  /**
   * @param id : assign id of this place. (see trait Element for detail explanation)
   */
  def setId(id: String) { this.id = id }
  
  /**
   * @return name of this place. (see trait Node for detail explanation)
   */
  def getName():String = name
  
  /**
   * @param name : assign name of this place. (see trait Node for detail explanation)
   */
  def setName(name: String) { this.name = name }
  
  /**
   * @return origin of this place. (see trait Element for detail explanation)
   */
  def getOrigin(): Map[String,String] = origin
  
  /**
   * @param origin : assign origin of this place. (see trait Element for detail explanation)
   */
  def setOrigin(origin: Map[String,String]) { this.origin = origin }
  
  /**
   * @return name of this place if toString method is called from this object class
   */
  override def toString = name
}
