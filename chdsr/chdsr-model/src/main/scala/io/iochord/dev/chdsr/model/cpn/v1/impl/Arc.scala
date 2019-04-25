package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1.Element

object Direction extends Enumeration {
	  val In, Out = Value
}

case class Arc[T] (
  id: String,
  place: Place[T],
  transition: Transition,
  direction: Direction.Value)
  extends Element {
  
  def getPlace(): Place[T] = { 
    place
  }
  
  def getTransition(): Transition = transition
  
  def getDirection():Direction.Value = direction
  
  def getId():String = id
}