package loan

import java.io._
import scala.io.Source._

object DuckLoan extends App {

  def withCloseable[R <: { def close() }, T](closeable: R)(f: R => T): T =
    try f(closeable)
    finally if (closeable != null) closeable.close

  val in = getClass.getResourceAsStream("loaned.txt")
  
  withCloseable(in) { in =>
    println(fromInputStream(in).mkString)
  }

}