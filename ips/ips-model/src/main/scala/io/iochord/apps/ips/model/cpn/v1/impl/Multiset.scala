package io.iochord.apps.ips.model.cpn.v1.impl

import scala.collection.mutable.Map
import scala.collection.IterableLike
import scala.collection.mutable.Builder
import io.iochord.apps.ips.model.cpn.v1._

/**
 *
 * @package ips-model
 * @author  Nur Ichsan Utama <nichsan@pusan.ac.kr>
 * @since   2019
 *
 * Multiset class that hold multiset value
 * T is generic type from token value
 *
 */

class Multiset[T] (val multiset: Map[(T,Long), Int], val coltype: Class[_] = null) {
  
  type coltype = T
  
  def getColtype(): Class[_] = { coltype }
  
  /**
   * @param elem : add specific number of token with time into this multiset
   * @return : return the new multiset after adding this element
   */
  def +(elem: (T,Long), n: Int) = {
    val ms = multiset
    
    var count = n
    if (ms.contains(elem)) {
      count += ms(elem)
    }
    if (count > 0)
      ms += (elem -> count)
    else if (count == 0)
      ms -= (elem)
    else
      throw new IllegalArgumentException("Cannot remove less than exist token")
  }
  
  /**
   * @param elem : delete only 1 token with time from this multiset
   * @return : return the new multiset after delete this element
   */
  def -(elem: (T,Long)) = this + (elem, -1)

  /**
   * @param elem : delete specific number token with time from this multiset
   * @return : return the new multiset after delete this element
   */
  def -(elem: (T,Long), n: Int) = this + (elem, -n)
  
  /**
   * @return multiset value which is in Map type
   */
  def getMap() = multiset
}
