package io.iochord.dev.chdsr.model.cpn.v1

import scala.collection.mutable.Map

trait Element {
	def getId: String
	def setId(inp: String)
	def getOrigin: Map[String,String]
	def setOrigin(inp: Map[String,String])
	def getAttributes: Map[String,Any]
	def setAttributes(inp: Map[String,Any])
}