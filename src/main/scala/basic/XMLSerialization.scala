package basic

class Personne(val name: String, val age: Int){
  //序列化操作
  def toXML() = {
    <person>
      <name>{name}</name>
      <age>{age}</age>
    </person>
  }
  //反序列化操作
  def fromXML(xml: scala.xml.Elem): Personne = {
    new Personne((xml \ "name").text, (xml \ "age").text.toInt)
  }
  override def toString() = "name = "+name + ",age = "+age
}

object XMLSerialization extends App{
  val p = new Personne("Lee",27)
  val xmlPerson = p.toXML()
  scala.xml.XML.save("./resources/person.xml", xmlPerson, "UTF-8", true, null)
  val loadPerson=scala.xml.XML.loadFile("./resources/person.xml")
  val p2 = p.fromXML(loadPerson)
  println(p2)
}
