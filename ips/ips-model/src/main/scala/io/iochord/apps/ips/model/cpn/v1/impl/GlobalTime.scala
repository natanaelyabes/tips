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
   * @return time : get time of the current simulation.
   */
  def getTime():Long = time
  
  /**
   * @param time : set time of the current simulation.
   */
  def setTime(time:Long) = this.time = time
}