package basic

import higherkindedTypes.A

class A

class B extends A {
  @deprecated def getName = "Class B"
}

object Annotation extends App {
  var b = new B()
  b.getName
}
