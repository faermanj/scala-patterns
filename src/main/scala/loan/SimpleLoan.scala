package loan

import java.io._
import scala.io.Source._

object SimpleLoan extends App {

  def withResource[T](resource: String)(f: InputStream => T):T = {
    var in: InputStream = null
    try {
      in = getClass.getResourceAsStream(resource)
      f(in)
    } finally {
      if (in != null) in.close
    }
  }

  withResource("loaned.txt") { in =>
    println(fromInputStream(in).mkString)
  }

}