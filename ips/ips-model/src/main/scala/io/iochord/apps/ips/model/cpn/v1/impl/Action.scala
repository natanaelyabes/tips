package io.iochord.apps.ips.model.cpn.v1.impl

import io.iochord.apps.ips.model.cpn.v1._

/**
*
* @package ips-model
* @author Nur Ichsan Utama <ichsan83@gmail.com>
* @since 2019
*
*/
class Action[B] { 
  
  private var actionFun:B => B = null
  
  def setActionFun(actionFun:B => B) = { this.actionFun = actionFun }
  
  def getActionFun():(B => B) = { this.actionFun }
  
  def computeActionFun(bind:B) = { this.actionFun(bind) }
}
