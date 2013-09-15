package tailcall

import scala.annotation.tailrec

object Fiboonacci extends App {
  def fibo(num: Int): BigInt = fibo(num, 1, 0)
  
  @tailrec def fibo(num: Int, nxt: BigInt, res: BigInt): BigInt =
    num match {
      case 0 => res
      case _ => fibo(num - 1, nxt + res, nxt)
    }

  println(fibo(999999))
}
