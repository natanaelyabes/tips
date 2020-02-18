package io.iochord.apps.ips.model.cpn.v1.impl

import scala.collection.mutable.HashMap
import collection.JavaConverters._

import io.iochord.apps.ips.model.cpn.v1._

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */

class CPNGraph {
  private var places = HashMap[String, Place[_]]()
  private var transitions = HashMap[String, Transition[_]]()
	private var arcs = List[Arc[_,_]]()
	
	/**
   * @return list of all transitions in this graph.
   */
	def allTransitions: List[Transition[_]] =  transitions.values.toList
	
	/**
   * @return list of all places in this graph.
   */
	def allPlaces: List[Place[_]] =  places.values.toList
	
	/**
   * @return list of all arcs in this graph.
   */
	def allArcs: List[Arc[_,_]] =  arcs
	
	/**
   * @param place : add new place in this graph.
   */
	def addPlace(place: Place[_]) = {
	  places += (place.getId() -> place)
	}
	
  /**
   * @param place : remove place in this graph.
   * If we remove place, automatically arc that connected to this place will be automatically deleted. (this function is doing that)
   */
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
	
	/**
   * @param transition : add new transition in this graph.
   */
  def addTransition[B](transition: Transition[B]) = {
    transitions += (transition.getId() -> transition)
  }
  
  /**
   * @param transition : remove transition in this graph.
   * If we remove transition, automatically arc that connected to this transition will be automatically deleted. (this function is doing that)
   */
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
	
  /**
   * @param arc : add new arc in this graph.
   * If we add new arc to this graph, we should add this arc in the transition because transition have variables in and out (which is in arcs and out arcs).
   * We should add arc in the transition as well because the simulation evaluation is checking enabled transition.
   * By adding arc directly in the transition it will be easier and faster to evaluate enabled transition. 
   */
	def addArc[T,B] (arc: Arc[T,B]) {	
	  import Direction._
	  if(arc.getDirection() == PtT)
	  	arc.getTransition().addIn(arc)
	  else
	  	arc.getTransition().addOut(arc)
	  
	  arcs = arc :: arcs
	}
	
	/**
   * @param place : merge place from other graph module to this graph.
   * If the place is already exist don't add to this graph. (checked by its id)
   */
	def addPlaceMerge(place: Place[_]) = {
	  if(places.get(place.getId()) == None)
	    addPlace(place)
	}
	
	/**
   * @param transition : merge transition from other graph module to this graph.
   * If the transition is already exist don't add to this graph. (checked by its id)
   */
	def addTransitionMerge[B](transition: Transition[B]) = {
	  if(transitions.get(transition.getId()) == None)
	    addTransition(transition)
	}
	
	/**
   * @param arc : merge arc from other graph module to this graph.
   * Replace the place and transition of this arc to place and transition that exist in this graph.
   * (Don't refer to the old of place and transition because this place and transition object only exist in the arc param in the different graph module.)
   */
	def addArcMerge[T,B](arc: Arc[T,B]) {
	  val place:Place[T] = places(arc.getPlace().getId()).asInstanceOf[Place[T]]
	  val transition:Transition[B] = transitions(arc.getTransition().getId()).asInstanceOf[Transition[B]]
	  arc.setPlace(place)
	  arc.setTransition(transition)
	  
	  if(transition.getIn().filter(a => a == arc).size == 0 && transition.getOut().filter(a => a == arc).size == 0)
	    addArc(arc)
	}
	
	/**
   * @param arc : remove specific arc in this graph.
   */
	def removeArc[T,B] (arc: Arc[T,B]) {
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
	
	/**
   * @return list of all places in this graph based on java.util.list
   */
	def getPlaces():java.util.List[Place[_]] = allPlaces.asJava
	
	/**
   * @return list of all transitions in this graph based on java.util.list
   */
	def getTransitions():java.util.List[Transition[_]] = allTransitions.asJava
	
	/**
   * @return list of all arcs in this graph based on java.util.list
   */
	def getArcs():java.util.List[Arc[_,_]] = arcs.asJava
}