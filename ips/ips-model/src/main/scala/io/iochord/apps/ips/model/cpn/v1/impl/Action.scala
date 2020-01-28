package io.iochord.apps.ips.model.cpn.v1.impl

import io.iochord.apps.ips.model.cpn.v1._

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 * Action class is used to give or change value of binding variable. Some value in the binding variable is possibly not yet defined in input arcs.
 * We can define it in Action class.
 *
 */
class Action[B] { 
  
  private var actionFun:B => B = null
  
  /**
   * @param actionFun : set action function. This action function will define or change value in the binding variable.
   * This function is transform from B to B type. The type of object is still the same. We only change the value of the variable inside this object. 
   */
  def setActionFun(actionFun:B => B) { this.actionFun = actionFun }
  
  /**
   * @return value of action function from actionFun variable
   */
  def getActionFun():(B => B) = { this.actionFun }
  
  
  /**
   * @param bind : compute the action function by sending parameter to actionFun variable.
   * Need to understand that setActionFun method previously defined above only accept function that tranform object (B => B)
   * This function need specific input, we set the input in this computeActionFun method
   */
  def computeActionFun(bind:B) { this.actionFun(bind) }
}
