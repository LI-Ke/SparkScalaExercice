package basic

case class Etud(val name:String) extends Ordered[Etud]{
  override def compare(that:Etud):Int={
    if(this.name == that.name)
      1
    else
      -1
  }
}

//将类型参数定义为T<:Ordered[T]
class Pair[T<:Ordered[T]](val first:T, val second:T){
  //比较的时候直接使用<符号进行对象间的比较
  def smaller()={
    if(first < second)
      first
    else
      second
  }
}

object OrderedViewBound extends App{
  val p = new Pair(Etud("Lee"), Etud("Lee2"))
  println(p.smaller())
}
