package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1.Element
import io.iochord.dev.chdsr.model.cpn.v1.Node
import lombok.Getter
import lombok.Setter

import scala.collection.generic._

class Place[T] (
  private var id: String,
  private var name: String,
  private var initialMarking: Multiset[T]) extends Element with Node {
  
  private var currentMarking = initialMarking
  
  private var in = List[Arc[_]]()
  private var out = List[Arc[_]]()
  
  def initState() = currentMarking = initialMarking
  
  def hasToken(token: Any) = { currentMarking hasToken token.asInstanceOf[T]}
  
  def hasTokenWithTime(tokenWithTime: Any) = { currentMarking hasTokenWithTime tokenWithTime.asInstanceOf[(T,Long)]}
  
  def removeTokenWithTime(token: Any) = {currentMarking - token.asInstanceOf[(T,Long)] }

  def addTokenWithTime(token: Any) = {currentMarking + token.asInstanceOf[(T,Long)] }
  
  def addIn(arc: Arc[_]) {
    in = arc :: in
  }
  
  def addOut(arc: Arc[_]) {
    out = arc :: out
  }
  
  def removeIn(arc: Arc[_]) {
    in = in.filterNot(_ == arc)
  }

  def removeOut(arc: Arc[_]) {
    out = out.filterNot(_ == arc)
  }
  
  def getcurrentMarking():Multiset[T] = currentMarking
  
  def setCurrentMarking(cm:Multiset[T]) { currentMarking = cm }
  
  def getIn(): List[Arc[_]] = in
  
  def getOut(): List[Arc[_]] = out
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def getName():String = name
  
  def setName(name: String) { this.name = name }
  
  override def toString = name
}
