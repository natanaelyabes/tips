package io.iochord.apps.ips.model.cpn.v1.impl

import io.iochord.apps.ips.model.cpn.v1._
import scala.collection.mutable.Map

/**
*
* @package ips-model
* @author Nur Ichsan Utama <ichsan83@gmail.com>
* @since 2019
*
*/
//PtT -> Place to Transition and TtP -> Transition to Place
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
  
  type coltype = T
  
  def getIsBase(): Boolean = isBase
  
  def setIsBase(isBase:Boolean) { this.isBase = isBase }
  
  def getPlace(): Place[T] = { place }
  
  def getTransition(): Transition[B] = transition
  
  def getDirection():Direction.Value = direction
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def getOrigin(): Map[String,String] = origin
  
  def setOrigin(origin: Map[String,String]) { this.origin = origin }
  
  def setTokenToBind(TtoB:coltype => Option[B]) = { TtoBV = TtoB }
  
  def setBindToToken(BtoT:B => coltype) = { BtoTV = BtoT }
  
  def setAddTime(addTime:B => Long) = { this.addTime = addTime }
  
  def setNoTokArcExp(noToken:Int) = { this.noTokArcExp = noToken }
}