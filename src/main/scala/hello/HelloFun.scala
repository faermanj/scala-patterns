package hello

object HelloFun extends App {
  List("Hello", "QCon", "SP", "2016", "?", "!!!")
    .map { _.toUpperCase }
    .filter { _ != "?" }
    .foreach { println }
}