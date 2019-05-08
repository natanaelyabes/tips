package io.iochord.dev.chdsr.model.cpn.v1.impl

import scala.collection.mutable.Map
import scala.collection.IterableLike
import scala.collection.mutable.Builder
import io.iochord.dev.chdsr.model.cpn.v1._

class Multiset[T] (val multiset: Map[(T,Long), Int], colset: Class[_]) {
  
  def coltype: Class[_] = colset
  
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
  
  def tokensLimitByTime(timeLT: Long): Map[(T, Long),Int] = for (((el,tm), no) <- multiset.filter(x => (x._1._2) <= timeLT)) yield ((el,tm), no)
  
  def hasToken(elem: T): Boolean = multiset.filter(x => x._1._1 equals(elem)).size > 0
  
  def hasTokenWithTime(elem: (T,Long)): Boolean = multiset.filter(x => x._1 equals(elem)).size > 0
  
  def equal(other: Any) = other match {
    case that: Multiset[T] =>
      if (this.multiset.size == that.multiset.size)
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
  
  def --(other: Multiset[T]): Multiset[T] = {
    if (this >>= other) {

      var ms = multiset
      other.multiset.foreach {
        case (el, c1) => ms.get(el) match {
          case Some(c2) =>
            val nc = c2 - c1
            if (nc > 0)
              ms = ms + (el -> nc)
            else
              ms = ms - el
        }
      }
      new Multiset(ms, colset)
    } else {
      throw new IllegalArgumentException("Cannot subtract larger multiset from multiset")
    }
  }
    
  def removeToken(token: (T,Long)) = this - token

  def addTokens(that: Any) = {
    that match {
      case other: Multiset[T] => this.multiset ++ other.multiset
      case _ => throw new IllegalArgumentException("Problem adding token")
    }
  }
    
  def +(elem: (T,Long), n: Int): Multiset[T] = {
    val ms = multiset
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
