package io.iochord.apps.ips.model.cpn.v1

import scala.collection.mutable.Map

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 * Element is different with Node. Place, Transition, and Arc is Element. But only Place and Transition is Node.
 *
 */

trait Element {
  
	/**
	 * @return id of this element (any class that extends this trait should implement this method)
	 */
	def getId: String
	
	
	/**
	 * @param inp : assign id of this element (any class that extends this trait should implement this method)
	 */
	def setId(inp: String)
	
	
	/**
	 * @return an origin of this element (any class that extends this trait should implement this method)
	 * Origin of this element is key-value pair "origin"->id_of_high_level_representation and "role"->specific_role_of_this element
	 */
	def getOrigin: Map[String,String]
	
	
	/**
	 * @param inp : assign origin of this element (any class that extends this trait should implement this method)
	 */
	def setOrigin(inp: Map[String,String])
}