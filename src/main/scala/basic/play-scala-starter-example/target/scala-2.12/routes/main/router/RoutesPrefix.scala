// @GENERATOR:play-routes-compiler
// @SOURCE:/local/like/ownCloud/PhD/learning/scala/play/play-scala-starter-example/conf/routes
// @DATE:Tue Sep 04 15:12:33 CEST 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
