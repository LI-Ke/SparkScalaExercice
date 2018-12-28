package basic

case class Person(var name:String, var age:Int) extends Comparable[Person]{
  def compareTo(o: Person): Int = {
    if (this.age > o.age) 1
    else if (this.age == o.age) 0
    else -1
  }
}

case class Students[S, T<: AnyVal](var name:S, var hignt: T)

class TypeVariableBound{
  def compare[T <: Comparable[T]](first: T, second: T) =  {
    if(first.compareTo(second) > 0)
      first
    else
      second
  }
}

object TypeVariableBound {
  def main(args: Array[String]): Unit = {
    val tvb = new TypeVariableBound
    println(tvb.compare("A", "B"))
    val s = Person("stephen",19)
    val j = Person("john",20)
    println(tvb.compare(s, j))
    //下面这条语句是不合法的，因为String类型不属于
    //AnyVal类层次结构
    // val S1=Students("john","170")
    //下面这两条语句都是合法的，因为
    //Int,Long类型都是AnyVal
    val S2 = Students("john",170.0)
    val S3 = Students("john",170L)
  }
}
