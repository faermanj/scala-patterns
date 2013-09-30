package tailcall

import scala.annotation.tailrec

object InfiniteStack {
  @tailrec
  def loop(x:Int):Int = {
    println(x)
    loop(-1*x)
  }

  def main(args: Array[String]) { loop(1) }
}