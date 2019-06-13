package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._
import lombok.Getter
import lombok.Setter

import scala.collection.generic._

class Place[T] (
  private var id: String,
  private var name: String,
  private var initialMarking: Multiset[T]) extends Element with Node {
  
  private var currentMarking = initialMarking
  
  private var in = List[Arc[_,_]]()
  private var out = List[Arc[_,_]]()
  
  def initState() = currentMarking = initialMarking
  
  def hasToken(token: Any) = { currentMarking hasToken token.asInstanceOf[T]}
  
  def hasTokenWithTime(tokenWithTime: Any) = { currentMarking hasTokenWithTime tokenWithTime.asInstanceOf[(T,Long)]}
  
  def removeTokenWithTime(token: Any) = {currentMarking - token.asInstanceOf[(T,Long)] }

  def addTokenWithTime(token: Any) = {currentMarking + token.asInstanceOf[(T,Long)] }
  
  def hasTokens(tokens: Any) = currentMarking >>= tokens.asInstanceOf[Multiset[T]]

  def removeTokens(tokens: Any) = currentMarking = currentMarking -- tokens.asInstanceOf[Multiset[T]]

  def addIn(arc: Arc[_,_]) {
    in = arc :: in
  }
  
  def addOut(arc: Arc[_,_]) {
    out = arc :: out
  }
  
  def removeIn(arc: Arc[_,_]) {
    in = in.filterNot(_ == arc)
  }

  def removeOut(arc: Arc[_,_]) {
    out = out.filterNot(_ == arc)
  }
  
  def getcurrentMarking():Multiset[T] = currentMarking
  
  def setCurrentMarking(cm:Multiset[T]) { currentMarking = cm }
  
  def getIn(): List[Arc[_,_]] = in
  
  def getOut(): List[Arc[_,_]] = out
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def getName():String = name
  
  def setName(name: String) { this.name = name }
  
  override def toString = name
}
