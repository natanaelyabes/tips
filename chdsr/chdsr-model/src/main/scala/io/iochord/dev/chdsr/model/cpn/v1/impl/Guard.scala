package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

class Guard { 
  
  var guardBind:List[Any] => Boolean = null
  
  def cond1[T <: Bind](cond:T => Boolean, inp:T):Boolean = {
    cond(inp)
  }
  
  def setGuardBind(eval: List[Any] => Boolean) {
    guardBind = eval
  }
  
  def evalGuard(inp:List[Any]):Boolean = {
    guardBind(inp)
  }
}
