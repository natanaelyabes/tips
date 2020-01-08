package io.iochord.apps.ips.model.cpn.v1.impl

import io.iochord.apps.ips.model.cpn.v1._

/**
*
* @package ips-model
* @author Nur Ichsan Utama <ichsan83@gmail.com>
* @since 2019
*
*/
class Guard[B] { 
  
  var guardBind:B => Boolean = null
  
  def setGuardBind(eval:B => Boolean) {
    guardBind = eval
  }
  
  def evalGuard(inp:List[B]):(Boolean,List[B]) = {
    val lbe = inp.filter(b => guardBind(b))
    (lbe.length > 0, lbe)
  }
}
