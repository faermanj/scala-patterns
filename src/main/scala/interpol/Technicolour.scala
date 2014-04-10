package interpol

import scala.collection.mutable.ListBuffer
import scala.util.Try
import scala.util.Random

object Colour extends Enumeration {
  val Blue, Red, Green, Yellow, Magenta, Cyan, Flicts = Value
}

object Technicolour extends App {
  val green = Colour.Green  

  println(colour"$green + Blue")
  
  println(colour"Blue with ${green} and Yellow vs. ${green} or Magenta")
  
  implicit class Colored(val sc: StringContext) extends AnyVal {
    //Interpolation magic below
    //Derived from http://www.monadzoo.com/blog/2013/01/06/scala-string-interpolation-it-happened/
    
    def colour(args: Any*): Colour.Value = {
      //Mutable WITH Immutable
      val strings = sc.parts.iterator
      val expressions = args.iterator
      var buf = new ListBuffer[Colour.Value]
      while (strings.hasNext) {
        buf ++= strings.next
        if (expressions.hasNext)
          buf ++= expressions.next.toString
      }
      val result = buf reduceLeft mix
      //println(s"[DEBUG] $buf = $result") // <= S Interpolator
      result
    }

    //Combine pairs of colours
    def mix(a: Colour.Value, b: Colour.Value): Colour.Value = (a, b) match {
      case (Colour.Green, Colour.Red) => Colour.Yellow
      case (Colour.Red, Colour.Green) => Colour.Yellow
      case (Colour.Red, Colour.Blue) => Colour.Magenta
      case (Colour.Blue, Colour.Red) => Colour.Magenta
      case (Colour.Blue, Colour.Green) => Colour.Cyan
      case (Colour.Green, Colour.Blue) => Colour.Cyan
      case (_, _) => Colour.Flicts
    }

    //Implicit conversion from String to Colours
    implicit def flying(colors: String): Seq[Colour.Value] =
      colors.split("\\s+")
        .map(t => Try { Colour.withName(t) })
        .filter { _.isSuccess }
        .map { _.get }
  }

}