package io.iochord.dev.chdsr.model.cpn.v1.impl

import scala.collection.mutable.HashMap

class CPNGraph {
  val places = HashMap[String, Place[_]]()
  val transitions = HashMap[String, Transition]()
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
	     if (a.direction == In)
	       a.transition.removeIn(a)
	     else a.transition.removeOut(a)
	     true
	   } else false
	  })
	}
	
  def addTransition(transition: Transition) = {
    
  }
  
  def removeTransition(transition: Transition) {
    
  }
	
	def addArc (arc: Arc[_]) {	
	  
	  import Direction._
	  if(arc.direction == In){
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
	  if(arc.direction == In)
	    arc.transition.removeIn(arc)
	  else
	    arc.transition.removeOut(arc)
	}
}

object CPNGraph {
  def apply() = new CPNGraph()
}