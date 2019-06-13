package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

//PtT -> Place to Transition and TtP -> Transition to Place
object Direction extends Enumeration {
	  val PtT, TtP = Value
}

class Arc[T] (
  private var id: String,
  private var place: Place[T],
  private var transition: Transition,
  private var direction: Direction.Value)
  
  extends Element {
  
  type coltype = T
  
  private var isBase: Boolean = false
  
  private var evalArcExpV:Any => T = null
  private var TtoBV:Any => Any = null
  private var BtoTV:Any => T = null
  
  def getIsBase(): Boolean = isBase
  
  def setIsBase(isBase:Boolean) { this.isBase = isBase }
  
  def getPlace(): Place[T] = { place }
  
  def setPlace(place:Place[T]) = { this.place = place }
  
  def getTransition(): Transition = transition
  
  def getDirection():Direction.Value = direction
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def transTokenToBind(TtoB:Any => Any) = { TtoBV = TtoB }
  
  def evalTokenToBind(inp:Any):Any = { TtoBV(inp) }
  
  def transBindToToken(BtoT:Any => T) = { BtoTV = BtoT }
  
  def evalBindToToken(inp:Any):T = { BtoTV(inp) }
  
  def setArcExp(evalArcExp:Any => T) = { evalArcExpV = evalArcExp }
  
  def evalArcExp(inp:Any):T = { evalArcExpV(inp) }
}