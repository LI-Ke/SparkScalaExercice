package spark.basic

import org.apache.spark.{SparkConf, SparkContext}

object Flatmap {
  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf().setAppName("flatmap").setMaster("spark://small1.common.lip6.fr:7077")
    val conf = new SparkConf().setAppName("flatmap").setMaster("local[*]")
/*    conf.set("spark.sql.shuffle.partitions", "130")
    conf.set("spark.cores.max", "130")
    conf.set("spark.executor.memory", "30G")*/

    val sc = new SparkContext(conf)
    val arr=sc.parallelize(Array(("A",1),("B",2),("Z",3)))
    arr.flatMap(x => x._1 + x._2).foreach(println)
    sc.stop()
  }

}
