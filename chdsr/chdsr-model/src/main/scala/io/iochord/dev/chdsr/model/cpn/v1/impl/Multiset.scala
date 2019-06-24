package io.iochord.dev.chdsr.model.cpn.v1.impl

import scala.collection.mutable.Map
import scala.collection.IterableLike
import scala.collection.mutable.Builder
import io.iochord.dev.chdsr.model.cpn.v1._

class Multiset[T] (val multiset: Map[(T,Long), Int], colset: Class[T]) {
  
  type coltype = T
  
  def colclass: Class[T]= colset
  
  def hasToken(elem: T): Boolean = multiset.filter(x => x._1._1 equals(elem)).size > 0
  
  def hasTokenWithTime(elem: (T,Long)): Boolean = multiset.filter(x => x._1 equals(elem)).size > 0
   
  def +(elem: (T,Long), n: Int): Multiset[T] = {
    val ms = multiset
    if(elem._1 == None)
      return new Multiset(ms, colset)
    
    var count = n
    if (ms.contains(elem)) 
      count += ms(elem)
    if (count > 0)
      ms += (elem -> count)
    else if (count == 0)
      ms -= (elem)
    else
      throw new IllegalArgumentException("Cannot remove less than exist token")
    new Multiset(ms, colset)
  }
  
  def +(elem: (T,Long)): Multiset[T] = this + (elem, 1)

  def -(elem: (T,Long)) = this + (elem, -1)

  def -(elem: (T,Long), n: Int) = this + (elem, -n)
}
