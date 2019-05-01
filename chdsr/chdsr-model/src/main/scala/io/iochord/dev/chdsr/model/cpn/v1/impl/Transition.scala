package io.iochord.dev.chdsr.model.cpn.v1.impl

case class Transition(
  id: String,
  name: String,
  guard: Guard) {
  
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