package io.iochord.apps.ips.model.cpn.v1.impl

import io.iochord.apps.ips.model.cpn.v1._

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 * Action class is used to assign or modify value in binding variable. Some value in the binding variable is possibly not yet defined in input arcs when checking enabling transition.
 * We can define how to assign or change those values in this Action class.
 *
 */
class Action[B] { 
  
  private var actionFun:B => B = null
  
  /**
   * @param actionFun : assign action function. This action function will assign or modify value in the binding variable.
   * This function is transform an object from B to B type (still same type which is the binding class). 
   */
  def setActionFun(actionFun:B => B) { this.actionFun = actionFun }
  
  /**
   * @return the action function from actionFun variable.
   * Noticed that the return from this function is not exact value but a function that transform an object from B to B type.
   */
  def getActionFun():(B => B) = { this.actionFun }
  
  
  /**
   * @param bind : compute the action function by assign parameter value to actionFun variable.
   * @return new bind
   * Need to understand that setActionFun method previously defined above only accept function that tranform object (B => B)
   * This function need specific input, we set the specific input value in this computeActionFun method
   */
  def computeActionFun(bind:B):B = { this.actionFun(bind) }
}
