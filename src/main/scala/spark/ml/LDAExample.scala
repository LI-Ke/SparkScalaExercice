package spark.ml

import java.util.Locale

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.{CountVectorizer, CountVectorizerModel, RegexTokenizer, StopWordsRemover}
import org.apache.spark.ml.linalg.{Vector => MLVector}
import org.apache.spark.mllib.clustering.{DistributedLDAModel, EMLDAOptimizer, LDA, OnlineLDAOptimizer}
import org.apache.spark.mllib.linalg.{Vector, Vectors}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * An example Latent Dirichlet Allocation (LDA) app. Run with
  * {{{
  * ./bin/run-example mllib.LDAExample [options] <input>
  * }}}
  * If you use it as a template to create your own app, please use `spark-submit` to submit your app.
  */
object LDAExample {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("LDA Example").setMaster("local[*]")

    // Load documents, and prepare them for LDA.
    val input: String = "./resources/ISTEX_Corpus.csv"
    val vocabSize: Int = 10000
    val stopwordFile: String = "./resources/stopwords_en.txt"
    val algorithm: String = "em"
    val k: Int = 10
    val maxIterations: Int = 10
    val maxTermsPerTopic: Int = 5

    // Load documents, and prepare them for LDA.
    val preprocessStart = System.nanoTime()
    val (corpus, vocabArray, actualNumTokens) =
      preprocess(conf, input, vocabSize, stopwordFile)
    corpus.cache()
    val actualCorpusSize = corpus.count()
    val actualVocabSize = vocabArray.length
    val preprocessElapsed = (System.nanoTime() - preprocessStart) / 1e9

    println()
    println(s"Corpus summary:")
    println(s"\t Training set size: $actualCorpusSize documents")
    println(s"\t Vocabulary size: $actualVocabSize terms")
    println(s"\t Training set size: $actualNumTokens tokens")
    println(s"\t Preprocessing time: $preprocessElapsed sec")
    println()

    // Run LDA.
    val lda = new LDA()

    val optimizer = algorithm.toLowerCase(Locale.ROOT) match {
      case "em" => new EMLDAOptimizer
      // add (1.0 / actualCorpusSize) to MiniBatchFraction be more robust on tiny datasets.
      case "online" => new OnlineLDAOptimizer().setMiniBatchFraction(0.05 + 1.0 / actualCorpusSize)
      case _ => throw new IllegalArgumentException(
        s"Only em, online are supported but got ${algorithm}.")
    }

    lda.setOptimizer(optimizer)
      .setK(k)
      .setMaxIterations(maxIterations)

    val startTime = System.nanoTime()
    val ldaModel = lda.run(corpus)
    val elapsed = (System.nanoTime() - startTime) / 1e9

    println(s"Finished training LDA model.  Summary:")
    println(s"\t Training time: $elapsed sec")

    if (ldaModel.isInstanceOf[DistributedLDAModel]) {
      val distLDAModel = ldaModel.asInstanceOf[DistributedLDAModel]
      val avgLogLikelihood = distLDAModel.logLikelihood / actualCorpusSize.toDouble
      println(s"\t Training data average log likelihood: $avgLogLikelihood")
      println()
    }

    // Print the topics, showing the top-weighted terms for each topic.
    val topicIndices = ldaModel.describeTopics(maxTermsPerTopic)
    val topics = topicIndices.map { case (terms, termWeights) =>
      terms.zip(termWeights).map { case (term, weight) => (vocabArray(term.toInt), weight) }
    }
    println(s"${k} topics:")
    topics.zipWithIndex.foreach { case (topic, i) =>
      println(s"TOPIC $i")
      topic.foreach { case (term, weight) =>
        println(s"$term\t$weight")
      }
      println()
    }
  }

  /**
    * Load documents, tokenize them, create vocabulary, and prepare documents as term count vectors.
    * @return (corpus, vocabulary as array, total token count in corpus)
    */
  private def preprocess(
                          conf: SparkConf,
                          paths: String,
                          vocabSize: Int,
                          stopwordFile: String): (RDD[(Long, Vector)], Array[String], Long) = {

    val sc = new SparkContext(conf)
    val spark = SparkSession.builder.config(conf).getOrCreate()
    // Get dataset of document texts
    // One document per line in each text file. If the input consists of many small files,
    // this can result in a large number of small partitions, which can degrade performance.
    // In this case, consider using coalesce() to create fewer, larger partitions.
    val loadDocs = spark.read.format("csv").option("delimiter", "\t").option("header","true").load(paths)
    loadDocs.createOrReplaceTempView("documents")
    val df1 = spark.sql("SELECT CONCAT(title, ' ',  abstract) AS docs FROM documents Where publication_year = 2001")
    val df = df1.filter(col("docs") isNotNull)
    val customizedStopWords: Array[String] = if (stopwordFile.isEmpty) {
      Array.empty[String]
    } else {
      val stopWordText = sc.textFile(stopwordFile).collect()
      stopWordText.flatMap(_.stripMargin.split("\\s+"))
    }
    val tokenizer = new RegexTokenizer()
      .setInputCol("docs")
      .setOutputCol("rawTokens")
    val stopWordsRemover = new StopWordsRemover()
      .setInputCol("rawTokens")
      .setOutputCol("tokens")
    stopWordsRemover.setStopWords(stopWordsRemover.getStopWords ++ customizedStopWords)
    val countVectorizer = new CountVectorizer()
      .setVocabSize(vocabSize)
      .setInputCol("tokens")
      .setOutputCol("features")

    val pipeline = new Pipeline()
      .setStages(Array(tokenizer, stopWordsRemover, countVectorizer))

    val model = pipeline.fit(df)
    val documents = model.transform(df)
      .select("features")
      .rdd
      .map { case Row(features: MLVector) => Vectors.fromML(features) }
      .zipWithIndex()
      .map(_.swap)

    (documents,
      model.stages(2).asInstanceOf[CountVectorizerModel].vocabulary,  // vocabulary
      documents.map(_._2.numActives).sum().toLong) // total token count
  }
}