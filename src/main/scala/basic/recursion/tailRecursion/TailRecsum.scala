package basic.recursion.tailRecursion

object TailRecsum {
  def tailrecsum(x: Int, running_total: Int = 0): Int = {
    if (x==0) {
      running_total
    } else {
      tailrecsum(x-1, running_total+x)
    }
  }

  def main(args: Array[String]): Unit = {
    val res = TailRecsum.tailrecsum(5)
    println(res)
  }
}
