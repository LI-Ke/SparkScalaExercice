package basic

import scala.collection.mutable.ListBuffer

object Subset {

  def getSubsets(nums: List[Int]): ListBuffer[ListBuffer[Int]] = {
    val ret: ListBuffer[ListBuffer[Int]] = new ListBuffer[ListBuffer[Int]]()

    for(i <- 0 to nums.length-1){
      dfs(0, ret, new ListBuffer[Int](), nums, i+1)
    }
    ret
  }

  def dfs(start: Int, ret: ListBuffer[ListBuffer[Int]], list: ListBuffer[Int], nums: List[Int], count: Int): Unit ={
    if (count == 0){
      val tmp = new ListBuffer[Int]
      tmp ++= list
      ret += tmp
      return
    }
    for(i <- start to nums.length-1){
      list += nums(i)
      dfs(i+1, ret, list, nums, count-1)
      list.remove(list.size-1)
    }
  }

  def main(args: Array[String]): Unit = {
    val nums = List(1, 2, 3, 4)
    val result = Subset.getSubsets(nums)
    println(result)
  }
}
