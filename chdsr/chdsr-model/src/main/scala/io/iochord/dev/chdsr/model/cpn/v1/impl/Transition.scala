package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1.Element
import io.iochord.dev.chdsr.model.cpn.v1.Node

class Transition(
  private var id: String,
  private var name: String,
  private var guard: Guard) extends Element with Node {
  
  private var in = List[Arc[_]]()
  private var out = List[Arc[_]]()
  
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
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def getName():String = name
  
  def setName(name: String) { this.name = name }
  
  def getGuard(): Guard = guard
  
  def setGuard(guard: Guard) { this.guard = guard }
  
  override def toString = name
}