package basic

object ExtractorSequence extends App{
  val list = List(List(1,2,3), List(2,3,4))
  list match {
    //_*表示匹配列表中的其它元素
    case List(List(one, two, three), _*) =>
      println("one = " + one + " two = " + two + " three = " + three)
    case _ => println("Other")
  }
  list match {
    //_表示匹配列表中的第一个元素
    //_*表示匹配List中的其它多个元素
    //这里采用的变量绑定的方式
    case List(_, x@List(_*), _*) => println(x)
    case _ => println("other list")
  }
}
