package implicitConversion

import java.io.File

object ImplicitFunction extends App{
  import ImplicitConversion._

  var x: Int = 3.5
  val f = new File("./resources/cosineSim.txt").read
  println(f)
}
