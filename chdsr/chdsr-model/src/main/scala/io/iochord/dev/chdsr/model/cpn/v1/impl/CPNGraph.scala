package io.iochord.dev.chdsr.model.cpn.v1.impl

import scala.collection.mutable.HashMap
import io.iochord.dev.chdsr.model.cpn.v1._

class CPNGraph {
  private var places = HashMap[String, Place[_]]()
  private var transitions = HashMap[String, Transition[_]]()
	private var arcs = List[Arc[_,_]]()
	
	def allTransitions: List[Transition[_]] =  transitions.values.toList
	def allPlaces: List[Place[_]] =  places.values.toList
	def allArcs: List[Arc[_,_]] =  arcs
	
	def addPlace(place: Place[_]) = {
	  places += (place.getId() -> place)
	}
	
	def removePlace (place: Place[_]) {
	  import Direction._
	  places.remove(place.getId())
	  
	  arcs = arcs.filterNot(a => {
	   if (a.getPlace().getId() == place.getId()) {
	     if (a.getDirection() == PtT)
	       a.getTransition().removeIn(a)
	     else a.getTransition().removeOut(a)
	     true
	   } else false
	  })
	}
	
  def addTransition[B<:Bind](transition: Transition[B]) = {
    transitions += (transition.getId() -> transition)
  }
  
  def removeTransition(transition: Transition[_]) {
    import Direction._
	  transitions.remove(transition.getId())
	  arcs = arcs.filterNot(a => {
	   if (a.getTransition().getId() == transition.getId())
	     true
	   else 
	     false
	  })
  }
	
	def addArc[T,B<:Bind] (arc: Arc[T,B]) {	
	  
	  import Direction._
	  if(arc.getDirection() == PtT)
	  	arc.getTransition().addIn(arc)
	  
	  else
	  	arc.getTransition().addOut(arc)
	  
	  arcs = arc :: arcs
	}
	
	def removeArc[T,B<:Bind] (arc: Arc[T,B]) {
	  val oldCount = arcs.size
	  arcs = arcs filterNot(a => a.getId() == arc.getId())
	  val newCount = arcs.size
	  
	  if(oldCount == newCount)
	    throw new RuntimeException("Arc " + arc + " is not present in the graph")
	  
	  import Direction._
	  if(arc.getDirection() == PtT)
	    arc.getTransition().removeIn(arc)
	  else
	    arc.getTransition().removeOut(arc)
	}
	
	def getPlaces():HashMap[String, Place[_]] = places
	def setPlaces(locplaces: HashMap[String, Place[_]]) { places = locplaces }
	
	def getTransitions():HashMap[String, Transition[_]] = transitions
	def setTransitions(loctransitions: HashMap[String, Transition[_]]) { transitions = loctransitions }
	
	def getArcs():List[Arc[_,_]] = arcs
	def setArcs(arcs: List[Arc[_,_]]) { this.arcs = arcs }
}

object CPNGraph {
  def apply() = new CPNGraph()
}