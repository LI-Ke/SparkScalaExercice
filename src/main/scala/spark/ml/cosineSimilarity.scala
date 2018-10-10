package spark.ml

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.linalg.distributed.{MatrixEntry, RowMatrix}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object cosineSimilarity extends App {
  val conf = new SparkConf().setAppName("Cosine Similarity").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val spark = SparkSession.builder.config(conf).getOrCreate()

  val rows = sc.textFile("./resources/cosineSim.txt").map{ line => {
    val values = line.split(" ").map(_.toDouble)
    Vectors.dense(values)
  }}.cache()

  val mat = new RowMatrix(rows)

  // Compute similar columns perfectly, with brute force.
  val exact = mat.columnSimilarities()

  val exactEntries = exact.entries.map { case MatrixEntry(i, j, u) => (i, j, u) }
  val dff = spark.createDataFrame(exactEntries).toDF("indexTopic1", "indexTopic2", "sim")
  dff.show(false)
}
