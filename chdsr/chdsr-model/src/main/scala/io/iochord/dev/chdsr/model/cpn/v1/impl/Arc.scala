package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

//PtT -> Place to Transition and TtP -> Transition to Place
object Direction extends Enumeration {
	  val PtT, TtP = Value
}

class Arc[T,B <:Bind] (
  private var id: String,
  private var place: Place[T],
  private var transition: Transition[B],
  private var direction: Direction.Value,
  private var coltype: Class[_] = null) extends Element {
  
  private var isBase: Boolean = false
  
  private var noTokArcExp:Int = 1
  private var arcExpV:coltype => Option[coltype] = null
  private var TtoBV:T => B = null
  private var BtoTV:B => T = null
  private var addTime:B => Long = null
  
  def getColtype(): Class[_] = { coltype }
  
  type coltype = T
  
  def getIsBase(): Boolean = isBase
  
  def setIsBase(isBase:Boolean) { this.isBase = isBase }
  
  def getPlace(): Place[T] = { place }
  
  def setPlace(place:Place[T]) = { this.place = place }
  
  def getTransition(): Transition[B] = transition
  
  def getDirection():Direction.Value = direction
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def setTokenToBind(TtoB:coltype => B) = { TtoBV = TtoB }
  
  def getTokenToBind():(coltype => B) = { this.TtoBV }
  
  def computeTokenToBind(token:coltype):B = { this.TtoBV(token) }
  
  def setBindToToken(BtoT:B => coltype) = { BtoTV = BtoT }
  
  def getBindToToken():(B => coltype) = { this.BtoTV }
  
  def computeBindToToken(bind:B):coltype = { this.BtoTV(bind) }
  
  def setArcExp(arcExp:coltype => Option[coltype]) = { this.arcExpV = arcExp }
  
  def getArcExp():(coltype => Option[coltype]) = { this.arcExpV }
  
  def computeArcExp(token:coltype) = { this.arcExpV(token) }
  
  def setAddTime(addTime:B => Long) = { this.addTime = addTime }
  
  def getAddTime():(B => Long) = { this.addTime }
  
  def computeAddTime(bind:B) = { this.addTime(bind) }
  
  def setNoTokArcExp(noToken:Int) = { this.noTokArcExp = noTokArcExp }
  
  def getNoTokArcExp():Int = { this.noTokArcExp }
}