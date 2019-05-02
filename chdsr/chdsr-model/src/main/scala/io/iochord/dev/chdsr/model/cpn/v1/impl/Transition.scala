package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1.Element
import io.iochord.dev.chdsr.model.cpn.v1.Node

case class Transition(
  id: String,
  name: String,
  guard: Guard) extends Element with Node {
  
  var in = List[Arc[_]]()
  var out = List[Arc[_]]()
  
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
  
  def getId():String = id
  
  def getName():String = name
}