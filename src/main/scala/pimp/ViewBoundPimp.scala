package pimp

trait Numberish 

case object one extends Numberish
case object two extends Numberish
case object three extends Numberish

object ViewBoundPimp extends App {
  def cube[T <% Int](a: T) = a * a * a
  
  implicit def toInt(x: Numberish) = x match {
    case `one` => 1
    case `two` => 2
    case `three` => 3
  }

  val answer = cube(three) + cube(two) + 2 * three + one
  println(answer)

}