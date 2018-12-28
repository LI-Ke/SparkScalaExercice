package basic

class Humain[T](var name:T)

class Etudiant[T](name:T) extends Humain(name)

class Professor[T,S](name:T,var age:S) extends Humain(name)

object GenericType extends App {
  println(new Etudiant[String]("John").name)
  println(new Professor[String, Int]("Mike", 35).name)
}
