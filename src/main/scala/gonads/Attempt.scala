package gonads

import scala.util.Try
import scala.util.Success
import scala.util.Failure

object Attempt extends App {
  //Try: Explicitly materialize exception and defer handling
  def divide(num:Int,denom:Int) = Try { num/denom }
  
  def name(small:Int) = Try {   
    small match {
      case 1 =>  "One"
      case 2 =>  "Two"
      case 3 =>  "Three"  
      case _ => throw new IllegalArgumentException
    }
  }
  
  //Composition with flatMap
  def divideAndName1(num:Int,denom:Int):Try[String] = divide(num,denom) flatMap name

  //Composition with for comprehension
  def divideAndName2(num:Int,denom:Int):Try[String] = for {
    result <- divide(num,denom)
    name <- name(result)
  } yield name
  
  val three = divideAndName1(9,3)
  val nan = divideAndName2(42,0)
  
  //Pattern matching on Try
  three match {
    case Success(s) => println(s)
    case Failure(t) => println(s"Oops: ${t.getMessage}")
  }
  
  println(nan)
  
}