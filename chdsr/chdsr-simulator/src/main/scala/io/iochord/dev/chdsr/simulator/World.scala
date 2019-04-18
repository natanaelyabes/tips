package io.iochord.dev.chdsr.simulator

import io.iochord.dev.chdsr.model.sbpnet.v1.Sbpnet
import io.iochord.dev.chdsr.model.sbpnet.v1.impl.SbpnetFactoryImpl

class World {
  def print(): Unit = {
    var x:Sbpnet = SbpnetFactoryImpl.getInstance().create()
    println("Hello, Scala!")
  }
}