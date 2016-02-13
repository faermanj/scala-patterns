package style

//from http://twitter.github.io/effectivescala/
object ListMatch extends App{
  val list = List(Option(1),Option(null),Option(3))
  
  list map { item =>
    item match {
      case Some(x) => println(x)
      case None => println("Oops")
    }
  }
  
  list map {
    case Some(x) => println(x)
    case None => println("Nice!")
  }
  
}