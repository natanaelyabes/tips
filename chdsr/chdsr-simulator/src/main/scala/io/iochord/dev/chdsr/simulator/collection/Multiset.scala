package io.iochord.dev.chdsr.simulator.collection

import scala.collection.mutable.Map

class Multiset[T] (val multiset: Map[(T,Long), Int]) extends Iterable[(Long,T)] {
  
  private def comparing(that: Any, cons: (Int, Int) => Boolean): Boolean = {
    that match {
      case other: Multiset[T] => {
        var cond = false
        for ((k, v) <- multiset if !cond) {
          if (other.multiset.contains(k))
            cond = cons(multiset(k), other.multiset(k))
        }
        cond
      }
      case _ => false
    }
  }
  
  def iterator: Iterator[(Long, T)] = for (((el,tm), no) <- multiset.iterator) yield (tm, el)
  
  def equal(other: Any) = other match {
    case that: Multiset[T] =>
      if (this.size == that.size)
        comparing(that, (x, y) => (x == y))
      else
        false
    case _ => false
  }
  
  def <<(other: Multiset[T]): Boolean = {
    comparing(other, (x, y) => (x < y))
  }
  
  def >>(other: Multiset[T]): Boolean = {
    other << this
  } 
  
  def <<=(other: Multiset[T]): Boolean = {
    comparing(other, (x, y) => (x <= y))
  }
  
  def >>=(that: Multiset[T]): Boolean =
    comparing(that, (x, y) => (x >= y))
    
  def +++(other: Multiset[T]) = this ++ other
  
  def removeToken(token: T) = this - token

  def addTokens(that: Any) = {
    that match {
      case other: Multiset[T] => this ++ other
      case _ => throw new IllegalArgumentException("Problem adding token")
    }
  }
    
  def +(n: Int, elem: T): Multiset[T] = {
    val ms = multiset
    var count = n
    if (ms.contains((elem,0L))) //curently use 0 but we have to use arbitrary time in here
      count += ms((elem,0L)) //curently use 0 but we have to use arbitrary time in here
    if (count > 0)
      ms += ((elem,0L) -> count)
    else if (count == 0)
      ms -= ((elem,0L))
    else
      throw new IllegalArgumentException("Cannot remove less than exist token")
    new Multiset(ms)
  }
  
  def +(elem: T): Multiset[T] = this + (1, elem)

  def -(elem: T) = this + (-1, elem)

  def -(n: Int, elem: T) = this + (-n, elem)
}
