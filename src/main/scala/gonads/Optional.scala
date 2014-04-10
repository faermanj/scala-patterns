package gonads

import scala.util.Random
import scala.util.Success
import scala.util.Success

object Optional extends App {
  def random = Random.nextInt(4)

  def unreliable:Integer = {
    val result = random // Is "random" a field or a method?
    if (result == 0) null else result
  }

  def optional = Option(unreliable)

  optional match {	
    case Some(x) => println(s"Got a $x! Yay!")
    case None => println("Not today :(")
  }

  println
  
  val names = Map(1 -> "One", 2 -> "Two", 3 -> "Three") 

  List.fill(10)(optional)
    .flatMap { _ map { names(_) } } // Is "names" a Map, a method or a field? 
    .zipWithIndex
    .foreach { case (name, index) => println(s"$index. $name") }

}