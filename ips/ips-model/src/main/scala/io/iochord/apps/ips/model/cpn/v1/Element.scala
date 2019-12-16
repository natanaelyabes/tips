package io.iochord.apps.ips.model.cpn.v1

import scala.collection.mutable.Map

trait Element {
	def getId: String
	def setId(inp: String)
	def getOrigin: Map[String,String]
	def setOrigin(inp: Map[String,String])
}