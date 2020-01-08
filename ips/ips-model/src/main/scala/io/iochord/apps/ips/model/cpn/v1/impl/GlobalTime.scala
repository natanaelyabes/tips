package io.iochord.apps.ips.model.cpn.v1.impl

/**
*
* @package ips-model
* @author Nur Ichsan Utama <ichsan83@gmail.com>
* @since 2019
*
*/
class GlobalTime(var time:Long) {
  
  def getTime():Long = time
  
  def setTime(time:Long) = this.time = time
}