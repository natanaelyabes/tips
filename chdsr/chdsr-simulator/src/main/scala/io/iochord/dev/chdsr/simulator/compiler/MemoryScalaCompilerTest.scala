package io.iochord.dev.chdsr.simulator.compiler

object MemoryScalaCompilerTest {
  def main(args: Array[String]) {
    val myclassSyntax = "new Simulation {\n"+
    "val test1 = \"apa aja\"\n"+
    "override def syntaxChecking(syntax:String):Unit ="+
        "{ printDebugging(\"Syntax Chcking\") }\n"+  
    "override def doStepSimulation(numbOfStep:Int,syntax:String):Unit ="+
        "{ printDebugging(\"Step Simulation\") }\n"+
      "override def doFastSimulation(classId: String):Unit ="+
        "{ printDebugging(classId) }\n"+
      "override def exploreState(classId: String):Unit ="+
        "{ println(\"Explore State\") }\n"+ 
      "override def calculateKPI(classId: String):Unit ="+
        "{ println(\"Calculate KPI\"); putVar(\"test1\",test1); putVar(\"test2\",new Coba1()) }\n "+  
      "def test(var1:String):Unit = "+
        "{ println(var1) }\n"+
      "class Coba1 { val varc1 = \"lain lagi\" }\n"+
    "}";
    
    val memoryScalaFactory = MemoryScalaCompiler(myclassSyntax)
    val memoryScala = memoryScalaFactory.getInstance
    memoryScala.calcKPI("Test aja")
    memoryScala.putVar("test3","halo_ok_deh")
    memoryScala.getVar("test1")
    val attrs = memoryScala.getVar("test2").getClass().getDeclaredField("varc1")
    attrs.setAccessible(true)
    println(attrs.get(memoryScala.getVar("test2")).asInstanceOf[String])
    //memoryScala.doStepSimulation(5,"Yu")
    //val mtd = memoryScala.getClass.getMethods.map(_.getName)
    //println(mtd.length)
    //mtd.foreach{println}
    //mtd.invoke(compiledClass)
  }
}