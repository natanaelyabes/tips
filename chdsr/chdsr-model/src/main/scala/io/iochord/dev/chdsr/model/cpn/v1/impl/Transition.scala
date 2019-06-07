package io.iochord.dev.chdsr.model.cpn.v1.impl

import io.iochord.dev.chdsr.model.cpn.v1._

class Transition(
  private var id: String,
  private var name: String,
  private var guard: Guard) extends Element with Node {
  
  private var in = List[Arc[_]]()
  private var out = List[Arc[_]]()
  
  def addIn(arc: Arc[_]) {
    if(arc.getIsBase())
      in = arc :: in
    else
      in = in ::: List[Arc[_]](arc)
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
  
  def getId(): String = id
  
  def setId(id: String) { this.id = id }
  
  def getName():String = name
  
  def setName(name: String) { this.name = name }
  
  def getGuard(): Guard = guard
  
  def setGuard(guard: Guard) { this.guard = guard }
  
  def isArcEnabled[B<:Bind]():(Boolean,List[B]) = { 
    val globtime = 200 //set dummy global time 
    
    val iterator = in.iterator
    
    var lbe = List[B]()
    
    if(iterator.hasNext)
    {
      val arc = iterator.next()
      val ms = arc.getPlace().getcurrentMarking()
      val listbinding = ms.multiset.keys.filter(_._2 < globtime).map(token => token match { 
        // we want to change from token to bind (need to change to bind to have common form of data type over all arcs for this transition
        case (colset:arc.coltype, _:Long) => { arc.evalTokenToBind(colset).asInstanceOf[B] }
      } ).toList
      
      for(binding <- listbinding) { //first arc in "in list" is base binding
        var base = binding
        var count_next = 1
        
        if(in.length == count_next)
        {
          lbe = base :: lbe
        }
        else {
          count_next = count_next + 1
          lbe = lbe ::: forRecursif(iterator, globtime, count_next, base)
        }
      }
    }
    
    (lbe.length > 0, lbe) 
  }
  
  def forRecursif[B<:Bind](iterator:Iterator[Arc[_]], globtime:Long, count:Int, b:B):List[B] = {
    var lbe = List[B]()
    
    if(iterator.hasNext)
    {
      val arc = iterator.next()
      print(arc.getId())
      val ms = arc.getPlace().getcurrentMarking()
      val listbinding = ms.multiset.keys.filter(_._2 < globtime).map(token => token match { 
        // we want to change from token to bind (need to change to bind to have common form of data type over all arcs for this transition
        case (colset:arc.coltype, _:Long) => { arc.evalTokenToBind(colset).asInstanceOf[B] }
      } ).toList
      
      for(binding <- listbinding) { //first arc in "in list" is base binding
        var base = b
        var count_next = count
        print(base+" ---- "+binding)
        print("\n")
        
        if(arc.getIsBase()) {
          base = binding 
          if(in.length == count_next)
          {
            lbe = b :: lbe
          }
          else {
            count_next = count_next + 1
            lbe = lbe ::: forRecursif(iterator, globtime, count_next, base)
          }
        }
        else {
          print(arc.evalArcExp(arc.evalBindToToken(base))+" --- "+arc.evalBindToToken(binding)+" --- "+(arc.evalArcExp(arc.evalBindToToken(base)) == arc.evalBindToToken(binding)))
          print("\n")
          print("Masuk loh "+count_next+ " - "+in.length)
          if(arc.evalArcExp(arc.evalBindToToken(base)) == arc.evalBindToToken(binding)) { 
            if(in.length == count_next)
            {
              lbe = b :: lbe
            }
            else {
              count_next = count_next + 1
              lbe = lbe ::: forRecursif(iterator, globtime, count_next, base)
            }
          }
        }
      }
    }  
    
    lbe
  }
  
  def isEnabled():Boolean = {
    val (isArcEn, lbe) = isArcEnabled()
    
    if(getGuard() != null) {
      println("Transition "+getId()+" is enabled "+getGuard().evalGuard(lbe))
      getGuard().evalGuard(lbe)
    }
    else
      isArcEn
  }
  
  override def toString = name
}