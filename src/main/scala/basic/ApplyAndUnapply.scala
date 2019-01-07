package basic

object EMail{
  //apply方法用于无new构造对象
  def apply(user: String, domain: String) = user + "@" + domain
  //unapply方法用于在模式匹配中充当extractor
  def unapply(str: String): Option[(String, String)] = {
    val parts = str.split("@")
    if(parts.length == 2)
      Some(parts(0), parts(1))
    else
      None
  }
}

object ApplyAndUnapply extends App{
  def patternMatching(s: String) = s match {
    //下面的匹配会导致调用EMail.unapply(email)
    case EMail(user, domain) => println("user = "+user+" domain = "+domain)
    //匹配非法邮箱
    case _ => println("non illegal email")
  }
  val email = EMail("li_ke", "yahoo.com")
  patternMatching(email)
  patternMatching("like")
}
