package basic

//利用<%符号对泛型S进行限定
//它的意思是S可以是Comparable类继承层次结构中实现了Comparable接口的类
//也可以是能够经过隐式转换得到的类,该类实现了Comparable接口
case class Stud[T, S <% Comparable[S]](var name:T, var heignt:S)

object ViewBound extends App{
  val s = Stud("john","170")
  val s2 = Stud("john", 170)
}
