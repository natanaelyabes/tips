package io.iochord.apps.ips.model.cpn.v1

import scala.collection.mutable.Map

/**
*
* @package ips-model
* @author Nur Ichsan Utama <ichsan83@gmail.com>
* @since 2019
*
*/
trait Element {
	def getId: String
	def setId(inp: String)
	def getOrigin: Map[String,String]
	def setOrigin(inp: Map[String,String])
}