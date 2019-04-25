package io.iochord.dev.chdsr.simulator.model

object Direction extends Enumeration {
	  val In, Out = Value
}

case class Arc[T] (
  id: String,
  place: Place[T],
  transition: Transition,
  direction: Direction.Value)
  extends Element {
  
}