package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

//PtT -> Place to Transition and TtP -> Transition to Place
object Direction extends Enumeration {
	  val PtT, TtP = Value
}

class Arc[T,B <:Bind] (
  private var id: String,
  private var place: Place[_],
  private var transition: Transition[_],
  private var direction: Direction.Value) extends Element {
  
  type coltype = T
  
  private var isBase: Boolean = false
  
  private var arcExpV:Any => Any = null
  private var TtoBV:Any => B = null
  private var BtoTV:Any => Any = null
  private var addTime:Any => Long = null
  
  def getIsBase(): Boolean = isBase
  
  def setIsBase(isBase:Boolean) { this.isBase = isBase }
  
  def getPlace(): Place[_] = { place }
  
  def setPlace(place:Place[_]) = { this.place = place }
  
  def getTransition(): Transition[_] = transition
  
  def getDirection():Direction.Value = direction
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def setTokenToBind(TtoB:Any => B) = { TtoBV = TtoB }
  
  def getTokenToBind():(Any => B) = { this.TtoBV }
  
  def computeTokenToBind(token:Any) = { this.TtoBV(token) }
  
  def setBindToToken(BtoT:Any => Any) = { BtoTV = BtoT }
  
  def getBindToToken():(Any => Any) = { this.BtoTV }
  
  def computeBindToToken(bind:Any) = { this.BtoTV(bind) }
  
  def setArcExp(arcExp:Any => Any) = { this.arcExpV = arcExp }
  
  def getArcExp():(Any => Any) = { this.arcExpV }
  
  def computeArcExp(token:Any) = { this.arcExpV(token) }
  
  def setAddTime(addTime:Any => Long) = { this.addTime = addTime }
  
  def getAddTime():(Any => Long) = { this.addTime }
  
  def computeAddTime(bind:Any) = { this.addTime(bind) }
}