package concurrency

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object AsychronousProgramming extends App {
  val  value1 ={
    Thread.sleep(2000)
    1
  }

  val value2 = Future{
    Thread.sleep(2000)
    2
  }
  println(value1)
  println(value2)
}
