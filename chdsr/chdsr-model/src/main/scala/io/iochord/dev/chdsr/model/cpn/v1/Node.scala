package io.iochord.dev.chdsr.model.cpn.v1

trait Node {
  
  def name: String

  override def toString = name
}