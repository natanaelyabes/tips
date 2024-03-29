package io.iochord.apps.ips.model.cpn.v1.impl.token

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 *
 */


/**
 * @param id, name, setupTime, jobList, maxToken is several attributes that could be used in the future for this resource type
 */
class Resource[T](var id:String, var name:String, var setupTime:Long, var jobList:List[T] = null, var maxToken:Int = 0) extends General(id:String, name:String)