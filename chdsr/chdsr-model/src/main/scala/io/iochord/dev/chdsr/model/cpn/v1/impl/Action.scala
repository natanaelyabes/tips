package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

class Action[B <:Bind] { 
  
  private var actionFun:B => B = null
  
  def setActionFun(actionFun:B => B) = { this.actionFun = actionFun }
  
  def getActionFun():(B => B) = { this.actionFun }
  
  def computeActionFun(bind:B) = { this.actionFun(bind) }
}
