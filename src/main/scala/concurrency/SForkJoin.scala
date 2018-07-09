package concurrency

import scala.concurrent.forkjoin.{ForkJoinPool, RecursiveTask}

object SForkJoin {

  class CountTask(start: Int, end: Int) extends RecursiveTask[Int] {

    val MAX = 1000

    override def compute(): Int = {
      val conCompute = (end - start) <= MAX
      conCompute match {
        case true => {
          (start to end).sum
        }
        case false => {
          val middle = (start + end) / 2
          val task1 = new CountTask(start, middle)
          val task2 = new CountTask(middle + 1, end)
          task1.fork()
          task2.fork()
          task1.join() + task2.join()
        }
      }
    }
  }

  def main(args: Array[String]) {

    val preprocessStart = System.nanoTime()
    val sum = (1 to 10000).sum
    println(sum)
    val preprocessElapsed = (System.nanoTime() - preprocessStart) / 1e9
    println(s"\t processing time: $preprocessElapsed sec")
    println()


    val forkJoinPool = new ForkJoinPool()
    val task = new CountTask(1, 10000)
    val start = System.nanoTime()
    val rs = forkJoinPool.submit(task)
    println(rs.get())
    val elapsed = (System.nanoTime() - start) / 1e9
    println(s"\t ForkJoin processing time: $elapsed sec")
    if (task.isCompletedAbnormally) {
      println(task.getException)
    }
    forkJoinPool.shutdown()
    println()


    val fJP = new ForkJoinPool(2)
    val ct = new CountTask(1, 10000)
    val st = System.nanoTime()
    val result = fJP.submit(ct)
    println(result.get())
    val elapse = (System.nanoTime() - st) / 1e9
    println(s"\t ForkJoin with 8 threads processing time: $elapse sec")
    if (ct.isCompletedAbnormally) {
      println(ct.getException)
    }
    fJP.shutdown()
  }
}