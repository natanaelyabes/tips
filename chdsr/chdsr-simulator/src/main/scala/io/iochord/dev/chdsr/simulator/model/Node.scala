package io.iochord.dev.chdsr.simulator.model

trait Node {
  
  def name: String

  override def toString = name
}