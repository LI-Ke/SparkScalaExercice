package implicitConversion

class ClassA {
  override def toString() = "This is Class A"
}
class ClassB {
  override def toString() = "This is Class B"
}
class ClassC {
  override def toString() = "This is  ClassC"
  def printC(c: ClassC) = println(c)
}
class ClassD

object ImplicitMultiFunctions{
  implicit def B2C(b: ClassB)={
    println("B2C")
    new ClassC
  }

  implicit def D2C(d: ClassD)={
    println("D2C")
    new ClassC
  }

  def main(args: Array[String]): Unit = {
    //下面的代码会进行两次隐式转换
    //因为ClassD中并没有printC方法
    //因为它会隐式转换为ClassC（这是第一次,D2C）
    //然后调用printC方法
    //但是printC方法只接受ClassC类型的参数
    //然而传入的参数类型是ClassB
    //类型不匹配，从而又发生了一次隐式转地换(这是第二次,B2C）
    //从而最终实现了方法的调用
    new ClassD().printC(new ClassB)
  }
}
