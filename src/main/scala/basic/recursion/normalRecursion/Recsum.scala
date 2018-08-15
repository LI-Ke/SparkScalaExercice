package basic.recursion.normalRecursion

object Recsum {
  def recsum(x: Int): Int = {
    if (x==1) {
      x
    } else {
      x + recsum(x-1)
    }
  }
  def main(args: Array[String]): Unit = {
    val res = Recsum.recsum(5)
    println(res)
  }
}
