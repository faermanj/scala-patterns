package evaluation

object ByValue extends App {
  def pronounce(number: Int) = {
    println(s"The number is $number !")
    println(s"The number is $number !")
  }

  pronounce(answer) //Where is anwser defined?
}