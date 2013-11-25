package evaluation

object ByName extends App{  
  def pronounce(answer: => Int) = {
	  println("The answer is "+answer+"!")
	  println("The answer is "+answer+"!")
  }
  
  pronounce (answer)
}