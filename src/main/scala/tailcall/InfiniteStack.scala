package tailcall

import scala.annotation.tailrec

object InfiniteStack {
  @tailrec
  def lupi(x:Int):Int = {
    println(x)
    lupi(-1*x)
  }

  def main(args: Array[String]) { lupi(1) }
}