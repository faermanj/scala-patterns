package pimp

trait Numberish 
case object one extends Numberish
case object two extends Numberish
case object three extends Numberish

object ViewBoundPimp extends App {
  implicit def toInt(x: Numberish) = x match {
    case `one` => 1
    case `two` => 2
    case `three` => 3
  }

  def cube[T <% Int](a: T) = a * a * a
  
  val answer = cube(three) 
             + cube(2) 
             + 2 * three 
             + one
  println(answer)
}



