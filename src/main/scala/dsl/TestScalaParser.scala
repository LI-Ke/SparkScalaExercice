package dsl

object TestScalaParser extends ScalaParser {
  def main(args: Array[String]) = {
    parse(expression, "5 + 4") match {
      case Success(result, _) => println(result)
      case Failure(msg, _) => println("FAILURE: " + msg)
      case Error(msg, _) => println("ERROR: " + msg)
    }
  }
}
