package io.iochord.apps.ips.model.cpn.v1.impl

import io.iochord.apps.ips.model.cpn.v1._

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 * Guard class is used to evaluate specific condition that will return boolean value (true or false).
 * Input value for the evaluation is based on value of the binding variable
 *
 */

class Guard[B] { 
  
  var guardBind:B => Boolean = null
  
  /**
   * @param eval : assign function that transform binding variable into boolean value
   */
  def setGuardBind(eval:B => Boolean) {
      guardBind = eval
    }
  
  /**
   * @param inp : assign input for guard evaluation which is list of binding values after enabling arc evaluation
   * @return (Boolean,List) : return boolean value true or false of this guard evaluation and list of binding values after guard evaluation
   */
  def evalGuard(inp:List[B]):(Boolean,List[B]) = {
    val lbe = inp.filter(b => guardBind(b))
    (lbe.length > 0, lbe)
  }
}
