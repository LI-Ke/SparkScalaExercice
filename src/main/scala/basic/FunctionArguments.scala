package basic

object FunctionArguments extends App{
  def convertIntToString(f:(Int)=>String) = f(4)

  val getString = convertIntToString((x: Int) => x + " s")
  println(getString)

  def multiplyBy(factor: Double) = (x : Double) => factor * x

  val multiplyBy10 = multiplyBy(10)

  println(multiplyBy10(50))

  // curry
  def multiplyBy2(factor: Double)(x : Double) = factor * x
  val res = multiplyBy2(10)(50)
  println(res)

  // Partial Applied Function
  val res2 = multiplyBy2(10)_
  println(res2(50))
}
