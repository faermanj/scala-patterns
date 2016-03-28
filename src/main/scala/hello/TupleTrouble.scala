package hello

object TupleTrouble extends App {
  
  def div(a:Int,b:Int):(Int,Int) = (a/b, a%b)
  
  val (result,remain) = div (9,4)
  println(s"result: $result remain: $remain")
  
}