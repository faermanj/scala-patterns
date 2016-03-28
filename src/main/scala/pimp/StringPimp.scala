package pimp

class StringPimp(original:String) {
  def strong = s"<strong>$original</strong>" 
  def em = s"<em>$original</em>"
}

object Example extends App {
	implicit def pimpMyString(str:String) = new StringPimp(str)
	println("Hello World".strong.em)
}

