package spark.basic

import org.apache.spark.{SparkConf, SparkContext}

object Flatmap {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("flatmap").setMaster("local[1]")
    val sc = new SparkContext(conf)
    val arr=sc.parallelize(Array(("A",1),("B",2),("C",3)))
    arr.flatMap(x => x._1 + x._2).foreach(println)
  }

}
