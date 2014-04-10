package loan

import java.io._
import scala.io.Source._

object BoundedLoan extends App {

  def withCloseable[R <: Closeable, T](closeable: R)(f: R => T): T =
    try f(closeable)
    finally if (closeable != null) closeable.close

  val in = getClass.getResourceAsStream("loaned.txt")
  
  withCloseable(in) { c =>
    println(fromInputStream(c).mkString)
  }

}