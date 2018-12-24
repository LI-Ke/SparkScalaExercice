package basic

object RegexExtractor extends App {
  val ipRegex="(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)".r
  for(ipRegex(one,two,three,four) <- ipRegex.findAllIn("192.168.1.1")) {
    println("IP 1:"+one)
    println("IP 2:"+two)
    println("IP 3:"+three)
    println("IP 4:"+four)
  }
}
