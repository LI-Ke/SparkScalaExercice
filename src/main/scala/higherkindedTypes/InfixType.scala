package higherkindedTypes

//定义Person类，两个泛型参数，分别是S，T,因此
//它是可以用中置表达式进行变量定义的
case class Person[S,T](val name: S, val age: T)

object InfixType extends App{
  //下面的代码是一种中置表达方法，相当于
  //val p:Person[String,Int]
  val p:String Person Int= Person("Lee",18)
  //中置表达式的模式匹配用法
  //模式匹配时可以直接用常量，也可以直接用变量
  p match {
    case "Lee" Person 18=> println("matching is ok")
    case name Person age=> println("name:"+name+"  age="+age)
  }
}
