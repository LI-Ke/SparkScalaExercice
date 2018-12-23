package basic

object MergeArray extends App {
  val a = Array("A", "B", "C")
  val b = Array("AB", "BC")
  val c = Array("ABC")
  val d = Array(a, b, c)
  val e = d.reduce((x, y) => (x ++ y))
  println(e.mkString(" "))
}
