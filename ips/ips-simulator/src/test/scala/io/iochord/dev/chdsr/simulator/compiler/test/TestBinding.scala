package io.iochord.dev.chdsr.simulator.compiler.test

object TestBinding {
  def main(args: Array[String]) {
    var lb:List[Int] = List[Int]()
    
    var inpPlaces:List[List[Int]] = List[List[Int]]()
    
    var p1:List[Int] = List[Int]()
    var p2:List[Int] = List[Int]()
    var p3:List[Int] = List[Int]()
    
    p1 = 1::p1
    p1 = 2::p1
    p1 = 3::p1
    
    p2 = 2::p2
    p2 = 4::p2
    p2 = 5::p2
    
    p3 = 7::p3
    p3 = 9::p3
    p3 = 2::p3
    
    inpPlaces = p1::inpPlaces
    inpPlaces = p2::inpPlaces
    inpPlaces = p3::inpPlaces
    
    for(j <- 1 to 100)
      print("Cek")
    
    println("")
    
    val start2 = System.currentTimeMillis()
    var ind2:Int = 0
    //Alternative Method
    recursive(inpPlaces,ind2)
    val duration2 = System.currentTimeMillis() - start2
    println("Alt Method Dur "+duration2)
    
    val start1 = System.currentTimeMillis()
    var ind1:Int = 1
    //Current Method
    for(p <- inpPlaces) {
      if(lb.length == 0)
          for(i <- p) {
            lb = i::lb    
          }
      else
        for(i <- p) {  
          for(b <- lb) {
            println("Place"+ind1+" : "+i) 
          }
        }  
      ind1 += 1
    }
    val duration1 = System.currentTimeMillis() - start1
    println("Current Method Dur "+duration1)
  }
  
  def recursive(inp:List[List[Int]],seq:Int):String = {
    if(seq != inp.length)
      for(i <- inp(seq)) {
        println("Place"+(seq+1)+" : "+i)
        recursive(inp,seq+1)
      }
    null
  }
}

