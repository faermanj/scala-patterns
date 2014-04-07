package gonads

import scala.util.Random
import scala.util.Success
import scala.util.Success

object Optional extends App {
  def random = Random.nextInt(4)
  
  def unreliable:Integer = {
    val result = random
    if (result == 0) null else result
  }
  
  def optional = Option(unreliable)  
  
  val names = Map(1->"One",2->"Two",3->"Three")
  
  optional match {
    case Some(x) => println(s"Got a $x! Yay!")
    case None => println("Not today :(")
  }

  println
  
  List.fill(10)(optional) 
  	  .flatMap {_ map {names(_)}} 
      .zipWithIndex
      .foreach {case (name,index) => println(s"$index. $name")}
      

}