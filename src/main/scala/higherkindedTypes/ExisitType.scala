package higherkindedTypes

object ExisitType extends App{
  def print(x: Array[_]) = println(x)

  def print2(x: Array[T] forSome {type T}) = println(x)

  //Map[_,_]相当于Map[T,U] forSome {type T;type U}
  def print3(x:Map[_,_])= println(x)

  print(Array("Super","lee"))
  print2(Array("Super","lee"))
  print3(Map("Super"->"lee"))
}
