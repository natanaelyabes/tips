package io.iochord.apps.ips.model.cpn.v1.impl.token

class Resource[T](id:String, name:String, setupTime:Long, jobList:List[T] = null, maxToken:Int = 0) extends General(id:String, name:String)