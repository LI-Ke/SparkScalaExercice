package higherkindedTypes

//下面定义了一个抽象类
//抽象类中用type关键字声明了一个抽象类型IndentityType
abstract class Person1{
  type IdentityType
  //方法的返回值类型被声明为抽象类型
  def getIdentityNo():IdentityType
}
//在子类中，对抽象类型进行具体化
class Student1 extends Person1{
  //将抽象类型具体化为String类型
  type IdentityType = String
  def getIdentityNo() = "123"
}
class Teacher1 extends Person1{
  //将抽象类型具体化为Int类型
  type IdentityType = Int
  def getIdentityNo() = 123
}

object AbstractType {
  def main(args: Array[String]): Unit = {
    //返回的是String类型
    println(new Student1().getIdentityNo())
    println(new Teacher1().getIdentityNo())
  }
}
