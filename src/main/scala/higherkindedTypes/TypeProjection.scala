package higherkindedTypes

import scala.reflect.runtime.universe.typeOf

class Outter{
  private var x:Int = 0
  private var i:Inner = new Outter.this.Inner
  //Outter#Inner类型投影的写法
  //可以接受任何outter对象中的Inner类型对象
  def print(i:Outter#Inner)= i
  class Inner{
    def test()= x
  }
}

class A extends Outter{
  private val i = new A.super.Inner
}

object TypeProjection extends App{
  val outter=new Outter
  val inner=new outter.Inner


  val outter2=new Outter
  val inner2=new outter2.Inner
  //下面的这个语句可以成功执行
  outter.print(inner2)
  //注意，下面的这条语句返回的仍然是false，我们只是对print方法中的
  //参数进行类型投影，并没有改变outter.Inner与outter2.Inner
  //是不同类的事实
  println(typeOf[outter.Inner]==typeOf[outter2.Inner])
}
