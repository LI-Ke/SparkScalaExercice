package basic.recursion.tailRecursion

object TailRecMax {
  def max(data: Array[Int], pos: Int, maxValue: Int): Int = {
    if (pos == data.length) {
      maxValue
    } else {
      max(data, pos + 1, if (data(pos) > maxValue) data(pos) else maxValue)
    }
  }

  def main(args: Array[String]): Unit = {
    val dataList = Array(4, 950, 123, 70, 2, 65)
    val res = TailRecMax.max(dataList, 0, Int.MinValue)
    println(res)
  }
}
