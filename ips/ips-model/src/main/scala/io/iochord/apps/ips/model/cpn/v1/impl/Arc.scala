package io.iochord.apps.ips.model.cpn.v1.impl

import io.iochord.apps.ips.model.cpn.v1._
import scala.collection.mutable.Map

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */


/**
 * Enumeration that define direction of this arcs
 * PtT -> Place to Transition and TtP -> Transition to Place
 */
object Direction extends Enumeration {
	  val PtT, TtP = Value
}

class Arc[T,B] (
  private var id: String,
  private var place: Place[T],
  private var transition: Transition[B],
  private var direction: Direction.Value) extends Element {
  
  private var isBase: Boolean = true
  
  var noTokArcExp:Int = 1
  var TtoBV:T => Option[B] = null
  var BtoTV:B => T = null
  var addTime:B => Long = null
  
  private var origin:Map[String,String] = null
  
  /**
   * Define type of coltype
   */
  type coltype = T
  
  /**
   * @return boolean value that define if this arc is base or not. (Base is arc that have simple expression that can be inverse directly using scala pattern matching.
   * (x,y) is base whereas (2*x, 2*y) is not base
   */
  def getIsBase(): Boolean = isBase
  
  /**
   * @param isBase : set isBase value for this arc. Determine if this arc should be categorized as base or not.
   */
  def setIsBase(isBase:Boolean) { this.isBase = isBase }
  
  /**
   * @param place : set place for this arc.
   * 1 Arc always consist of pair place-transition
   */
  def setPlace(place:Place[T]) { this.place = place }
  
  /**
   * @param transition : set transition for this arc.
   * 1 Arc always consist of pair place-transition
   */
  def setTransition(transition:Transition[B]) { this.transition = transition }
  
  /**
   * @return place of this arc.
   * 1 Arc always consist of pair place-transition
   */
  def getPlace(): Place[T] = place
  
  /**
   * @return transition of this arc.
   * 1 Arc always consist of pair place-transition
   */
  def getTransition(): Transition[B] = transition
  
  /**
   * @return direction of this arc.
   */
  def getDirection():Direction.Value = direction
  
  /**
   * @return id of this arc. (see trait Element for detail explanation)
   */
  def getId(): String = id
  
  /**
   * @param id : set id of this arc. (see trait Element for detail explanation)
   */
  def setId(id: String) { this.id = id }
  
  /**
   * @return origin of this arc. (see trait Element for detail explanation)
   */
  def getOrigin(): Map[String,String] = origin
  
  /**
   * @param origin : set origin of this arc. (see trait Element for detail explanation)
   */
  def setOrigin(origin: Map[String,String]) { this.origin = origin }
  
  /**
   * @param TtoB : set function that transform token coltype to binding type.
   */
  def setTokenToBind(TtoB:coltype => Option[B]) { TtoBV = TtoB }
  
  /**
   * @param BtoT : set function that transform binding type to token coltype.
   */
  def setBindToToken(BtoT:B => coltype) { BtoTV = BtoT }
  
  /**
   * @param token : compute TtoBV function based on parameter value of token.
   * @return : Option[B] it can be None or some value
   */
  def computeTokenToBind(token:coltype):Option[B] = { TtoBV(token) }
  
  /**
   * @param addTime : set function that transform binding type to Long
   */
  def setAddTime(addTime:B => Long) = { this.addTime = addTime }
  
  /**
   * @param noToken : set number of token that will be consumed or generated by this arc expression.
   */
  def setNoTokArcExp(noToken:Int) = { this.noTokArcExp = noToken }
}