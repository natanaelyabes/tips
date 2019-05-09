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
  
  var evalArcExp:T => T = null
  
  def getPlace(): Place[T] = { place }
  
  def setPlace(place:Place[T]) = { this.place = place }
  
  def getTransition(): Transition = transition
  
  def getDirection():Direction.Value = direction
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def setArcExp(evalArcExp:T => T) = { this.evalArcExp = evalArcExp }
  
  def transArcExp(inp:T):T = { evalArcExp(inp) }
}