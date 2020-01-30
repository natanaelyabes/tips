package io.iochord.apps.ips.model.cpn.v1

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 * Element is different with Node. Place, Transition, and Arc is Element. But only Place and Transition is Node.
 *
 */

trait Node {
  
  /**
   * @return name of this element (any class that extends this trait should implement this method)
   */
  def getName: String
  
  
  /**
   * @param name : set name of this element (any class that extends this trait should implement this method)
   */
  def setName(name:String)
}