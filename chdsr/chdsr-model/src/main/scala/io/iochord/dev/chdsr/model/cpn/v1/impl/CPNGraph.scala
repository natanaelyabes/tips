package io.iochord.dev.chdsr.model.cpn.v1.impl

import scala.collection.mutable.HashMap

class CPNGraph {
  var places = HashMap[String, Place[_]]()
  var transitions = HashMap[String, Transition]()
	var arcs = List[Arc[_]]()
	
	def allTransitions: List[Transition] =  transitions.values.toList
	def allPlaces: List[Place[_]] =  places.values.toList
	def allArcs: List[Arc[_]] =  arcs
	
	def addPlace(place: Place[_]) = {
	  places += (place.id -> place)
	}
	
	def removePlace(place: Place[_]) {
	  import Direction._
	  places.remove(place.id)
	  arcs = arcs.filterNot(a => {
	   if (a.place.id == place.id) {
	     if (a.direction == PtT)
	       a.transition.removeIn(a)
	     else a.transition.removeOut(a)
	     true
	   } else false
	  })
	}
	
  def addTransition(transition: Transition) = {
    transitions += (transition.id -> transition)
  }
  
  def removeTransition(transition: Transition) {
    import Direction._
	  transitions.remove(transition.id)
	  arcs = arcs.filterNot(a => {
	   if (a.transition.id == transition.id) {
	     if (a.direction == PtT)
	       a.place.removeOut(a)
	     else a.place.removeIn(a)
	     true
	   } else false
	  })
  }
	
	def addArc (arc: Arc[_]) {	
	  
	  import Direction._
	  if(arc.direction == PtT){
	  	arc.transition.addIn(arc)
	  	arc.place.addOut(arc)
	  }
	  else{
	  	arc.transition.addOut(arc)
	  	arc.place.addIn(arc)
	  }
	  
	  arcs = arc :: arcs
	}
	
	def removeArc(arc: Arc[_]) {
	  val oldCount = arcs.size
	  arcs = arcs filterNot(a => a.id == arc.id)
	  val newCount = arcs.size
	  
	  if(oldCount == newCount)
	    throw new RuntimeException("Arc " + arc + " is not present in the graph")
	  
	  import Direction._
	  if(arc.direction == PtT)
	    arc.transition.removeIn(arc)
	  else
	    arc.transition.removeOut(arc)
	}
	
	def getPlaces():HashMap[String, Place[_]] = places
	def setPlaces(locplaces: HashMap[String, Place[_]]) { places = locplaces }
	
	def getTransitions():HashMap[String, Transition] = transitions
	def setTransitions(loctransitions: HashMap[String, Transition]) { transitions = loctransitions }
	
	def getArcs():List[Arc[_]] = arcs
	def setArcs(locarcs: List[Arc[_]]) { arcs = locarcs }
}

object CPNGraph {
  def apply() = new CPNGraph()
}