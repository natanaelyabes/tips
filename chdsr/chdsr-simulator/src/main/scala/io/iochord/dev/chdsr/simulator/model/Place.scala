package io.iochord.dev.chdsr.simulator.model

case class Place[T] (
  id: String,
  name: String,
  initialMarking: T) extends Element with Node {

  var currentMarking = initialMarking
  
  def initState() = currentMarking = initialMarking
  
  def hasTokens(tokens: Any) = { }

  def removeTokens(tokens: Any) = { }

  def addTokens(tokens: Any) = { }
  
  var in = List[Arc[_]]()
  var out = List[Arc[_]]()
  def addIn(arc: Arc[_]) {
    in = arc :: in
  }
  def addOut(arc: Arc[_]) {
    out = arc :: out
  }
}
