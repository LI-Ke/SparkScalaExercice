package basic

class Stu(var name: String){
  //利用柯里化函数的定义方式，将函数的参数利用
  //implicit关键字标识
  //这样的话，在使用的时候可以不给出implicit对应的参数
  def formatStud()(implicit outputFormat: OutputFormat)={
    outputFormat.first + " " + this.name + " " + outputFormat.second
  }
}

class OutputFormat(var first: String, val second: String)

object ImplicitParameter {
  def main(args: Array[String]): Unit = {
    //程序中定义的变量outputFormat被称隐式值
    implicit val outputFormat = new OutputFormat("<<", ">>")
    //在.formatStudent()方法时，编译器会查找类型
    //为OutputFormat的隐式值,本程序中定义的隐式值
    //为outputFormat
    println(new Stu("john").formatStud())
  }
}
