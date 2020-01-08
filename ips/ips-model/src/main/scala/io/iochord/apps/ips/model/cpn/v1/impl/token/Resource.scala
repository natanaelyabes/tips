package io.iochord.apps.ips.model.cpn.v1.impl.token

/**
*
* @package ips-model
* @author Nur Ichsan Utama <ichsan83@gmail.com>
* @since 2019
*
*/
class Resource[T](id:String, name:String, setupTime:Long, jobList:List[T] = null, maxToken:Int = 0) extends General(id:String, name:String)