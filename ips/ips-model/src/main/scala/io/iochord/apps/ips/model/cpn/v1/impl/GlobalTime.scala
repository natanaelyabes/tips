package io.iochord.apps.ips.model.cpn.v1.impl

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */

class GlobalTime(var time:Long) {
  
  /**
   * @return time : get current global time of the simulation.
   */
  def getTime():Long = time
  
  /**
   * @param time : assign current global time of the simulation.
   */
  def setTime(time:Long) = this.time = time
}