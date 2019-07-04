package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

class Action[B <:Bind] { 
  
  private var actionFun:Any => Any = null
  private var TtoB:Any => B = null
  private var BtoT:Any => Any = null
  
  def setTokenToBind(TtoB:Any => B) = { this.TtoB = TtoB }
  
  def getTokenToBind():(Any => B) = { this.TtoB }
  
  def computeTokenToBind(token:Any) = { this.TtoB(token) }
  
  def setBindToToken(BtoT:Any => Any) = { this.BtoT = BtoT }
  
  def getBindToToken():(Any => Any) = { this.BtoT }
  
  def computeBindToToken(bind:Any) = { this.BtoT(bind) }
  
  def setActionFun(actionFun:Any => Any) = { this.actionFun = actionFun }
  
  def getActionFun():(Any => Any) = { this.actionFun }
  
  def computeActionFun(token:Any) = { this.actionFun(token) }
}
