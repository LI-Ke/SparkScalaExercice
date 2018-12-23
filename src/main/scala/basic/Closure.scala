package basic

object Closure extends App {
  // Free Variable
  var more = 1

  val fun = (x:Int) => x + more

  println(fun(10))

  more = 20

  println(fun(10))
}
