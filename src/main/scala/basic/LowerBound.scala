package basic

class Pair1[T](val first:T,val second:T){
  //下界通过[R >: T]的意思是泛型R的类型必须是T的超类
  def replaceFirst[R >: T](newFirst:R)= new Pair1[R](newFirst,second)
  override def toString()=first+"---"+second
}

//Book类
class Book(val name:String){
  override def toString()="name--"+name
}
//Book子类Ebook
class Ebook(name:String) extends Book(name)
//Book子类Pbook
class Pbook(name:String) extends Book(name)
//Pbook子类,WeridBook
class WeirdBook(name:String) extends Pbook(name)

object LowerBound extends App{

  val first = new Ebook("ebook")
  val second = new Pbook("paper book")


  val p1 = new Pair1(first,second)
  println(p1)
  //scala> val p1 = new Pair1(first,second)
  //p1: Pair1[Book] = name--ebook---name--paper book
  //Ebook,Pbook，最终得到的类是Pair1[Book]

  val newFirst = new Book("generic pBook")
  val p2 = p1.replaceFirst(newFirst)
  //p2: Pair1[Book] = name--generic pBook---name--paper book
  println(p2)

  val weirdFirst:WeirdBook = new WeirdBook("weird pBook")
  val p3 = p1.replaceFirst(weirdFirst)
  //p3: Pair1[Book] = name--weird pBook---name--paper book

  val p4 = new Pair1(second,second)
  //p4: Pair1[Pbook] = name--paper book---name--paper book
  println(p4)

  val thirdBook = new Book("Super Books")
  val p5 = p4.replaceFirst(thirdBook)
  println(p5)

  //下面这条语句会报错
  //type mismatch; found : cn.scala.xtwy.lowerbound.Pair1[cn.scala.xtwy.lowerbound.Pbook] required: cn.scala.xtwy.lowerbound.Pbook
  //val p6:Pbook = p4.replaceFirst(weirdFirst)

  //当限定返回变量类型时，例如val p6:Pbook=p4.replaceFirst(weirdFirst)，由于p4为Pair1[Pbook]，
  //也即T为Pbook类型，而replaceFirst(weirdFirst)中的weirdFirst为Pbook的子类，违反了R>:T的下界限定，从而编译出错。

  val p6 = p4.replaceFirst(weirdFirst)
  println(p6)
  //p6: Pair1[Pbook] = name--weird pBook---name--paper book
}
