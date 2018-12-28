package basic

sealed abstract class Person

case class Student(name: String, age: Int, studentNo: Int) extends Person
case class Teacher(name: String, age: Int, teacherNo: Int) extends Person
case class NoBody(name: String) extends Person

object CaseClass extends App{
  val p: Person = Student("john", 18, 1024)
  p match {
    case Student(name, age, studentNo) => println(name + ":" + age + ":" + studentNo)
    case Teacher(name, age, teacherNo) => println(name + ":" + age + ":" + teacherNo)
    case NoBody(name) => println(name)
  }
  println(p.hashCode())

  val s:Person = Student("john",18,1024)
  val t:Person = Teacher("steven", 35, 100)
  //这边仅仅给出了匹配Student的情况，在编译时
  //编译器会提示
  //match may not be exhaustive. It would fail on the following inputs: Nobody(_), Teacher(_, _, _)
  s match{
    case Student(name,age,studentNo)=>println("Student")
  }

  // Option Demo
  val m=Map("hive"->2,"spark"->3,"Spark MLlib"->4)
  def mapPattern(t: String) = m.get(t) match {
    case Some(x) => { println(x)
      x
    }
    case None => { println("None")
      -1
    }
  }
  println(mapPattern("spark"))
  println(mapPattern("Hive"))
}
