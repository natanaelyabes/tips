package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

class Action[B <:Bind] { 
  
  private var actionFun:Any => Any = null
  private var TtoBV:Any => B = null
  private var BtoTV:Any => Any = null
  
  def setTokenToBind(TtoB:Any => B) = { TtoBV = TtoB }
  
  def getTokenToBind():(Any => B) = { this.TtoBV }
  
  def computeTokenToBind(token:Any) = { this.TtoBV(token) }
  
  def setBindToToken(BtoT:Any => Any) = { BtoTV = BtoT }
  
  def getBindToToken():(Any => Any) = { this.BtoTV }
  
  def computeBindToToken(bind:Any) = { this.BtoTV(bind) }
  
  def setActionFun(actionFun:Any => Any) = { this.actionFun = actionFun }
  
  def getActionFun():(Any => Any) = { this.actionFun }
  
  def computeActionFun(token:Any) = { this.actionFun(token) }
}
