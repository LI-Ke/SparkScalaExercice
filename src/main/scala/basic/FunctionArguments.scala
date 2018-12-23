package basic

object FunctionArguments extends App{
  def convertIntToString(f:(Int)=>String) = f(4)

  val getString = convertIntToString((x: Int) => x + " s")
  println(getString)

  def multiplyBy(factor: Double) = (x : Double) => factor * x

  val multiplyBy10 = multiplyBy(10)

  println(multiplyBy10(50))
}
