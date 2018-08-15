package basic.recursion.tailRecursion

import scala.annotation.tailrec

object TailRecMax {
  def max(data: Array[Int]): Int = {
    @tailrec
    def max0(data: Array[Int], pos: Int, max: Int): Int = {
      if (pos == data.length) {
        max
      } else {
        max0(data, pos + 1, if (data(pos) > max) data(pos) else max)
      }
    }
    max0(data, 0, Int.MinValue)
  }

  def main(args: Array[String]): Unit = {
    val dataList = Array(4, 950, 123, 70, 2, 65)
    val res = TailRecMax.max(dataList)
    println(res)
  }
}
