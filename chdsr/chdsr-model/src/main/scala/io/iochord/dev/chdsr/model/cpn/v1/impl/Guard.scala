package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

class Guard { 
  
  def cond1[T <: Bind](cond:T => Boolean, inp:T):Boolean = {
    cond(inp)
  }
  
  /*
  def cond2(cond:List[Any] => Boolean, inp:Any):Boolean = {
    cond(inp)
  }
  */
}
