package higherkindedTypes

import scala.reflect.runtime.universe._

object ManifestType extends App{
  def print1[T](x: List[T])(implicit m: Manifest[T]) = {
    if (m <:< manifest[String])
      println("List of String")
    else
      println("List of non string")
  }
  print1(List("one", "two"))
  print1(List(1, 2))
  print1(List("one", 2))

  def patternMatch[A : TypeTag](xs: List[A]) = typeOf[A] match {
    //利用类型约束进行精确匹配
    case t if t =:= typeOf[String] => "list of strings"
    case t if t <:< typeOf[Int] => "list of ints"
  }
  println(patternMatch(List(1,2)))
}
