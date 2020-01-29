package io.iochord.apps.ips.model.cpn.v1.impl

import io.iochord.apps.ips.model.cpn.v1._

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */

class Guard[B] { 
  
  var guardBind:B => Boolean = null
  
  /**
   * @param eval : set function that tranform variables binding into boolean value
   */
  def setGuardBind(eval:B => Boolean) {
      guardBind = eval
    }
  
  /**
   * @param inp : accept input of list all binding before guard evaluation
   * @return (Boolean,List) : return boolean value true or false of this guard evaluation and list of binding values.
   */
  def evalGuard(inp:List[B]):(Boolean,List[B]) = {
    val lbe = inp.filter(b => guardBind(b))
    (lbe.length > 0, lbe)
  }
}
