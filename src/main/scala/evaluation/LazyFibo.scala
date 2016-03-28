package evaluation

import scala.annotation.tailrec

object FiboFight extends App {
  
  def fibo(num: Int): BigInt = fiborec(num, 1, 0)
  
  @tailrec def fiborec(num: Int, 
      nxt: BigInt, 
      res: BigInt): BigInt = num match {
      case 0 => res
      case _ => fiborec(num - 1, nxt + res, nxt)
    }
  
  println(fibo(1000000))
  
  lazy val fibs:Stream[BigInt] =
     0 #:: 
     1 #:: 
     (fibs zip fibs.tail).map{ t => t._1 + t._2 } 
  println(fibs drop(100) take(1) head)
  
}