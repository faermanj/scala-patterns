package extractors

import java.math.BigInteger

object PartialFun extends App {

  def isPrime(x: Int) = BigInteger
    .valueOf(x)
    .isProbablePrime(Integer.MAX_VALUE)

  object Prime {
    def unapply(x: Int): Option[Int] =
      if (isPrime(x)) Some(x)
      else None
  }

  object Double {
    def unapply(x: Int): Option[Int] =
      if (x % 2 == 0) Some(x)
      else None
  }

  (1 to 20) collect {
    case Prime(x) => s"$x is a prime"
    case Double(x) => s"$x is a double"
  } foreach println

}