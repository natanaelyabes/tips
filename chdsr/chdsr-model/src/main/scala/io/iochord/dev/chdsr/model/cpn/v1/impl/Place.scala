package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

import scala.collection.generic._
import scala.collection.mutable.Map

class Place[T] (
  private var id: String,
  private var name: String,
  private var initialMarking: Multiset[T]) extends Element with Node {
  
  private var currentMarking = initialMarking
  
  private var origin:Map[String,String] = null
  private var attributes:Map[String,Any] = null
  
  def initState() = currentMarking = initialMarking
  
  def hasTokenWithTime(tokenWithTime: Any) = { currentMarking hasTokenWithTime tokenWithTime.asInstanceOf[(T,Long)]}
  
  def removeTokenWithTime(tokenWithTime: Any, noToken: Int) {currentMarking - (tokenWithTime.asInstanceOf[(T,Long)],noToken) }

  def addTokenWithTime(tokenWithTime: Any, noToken: Int)  { currentMarking + (tokenWithTime.asInstanceOf[(T,Long)],noToken) }
  
  def getcurrentMarking():Multiset[T] = currentMarking
  
  def setCurrentMarking(cm:Multiset[T]) { currentMarking = cm }
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def getName():String = name
  
  def setName(name: String) { this.name = name }
  
  def getOrigin(): Map[String,String] = origin
  
  def setOrigin(origin: Map[String,String]) { this.origin = origin }
  
  def getAttributes(): Map[String,Any] = attributes
  
  def setAttributes(attributes: Map[String,Any]) { this.attributes = attributes }
  
  override def toString = name
}
