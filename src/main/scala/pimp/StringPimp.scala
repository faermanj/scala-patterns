package pimp

class StringPimp(original:String) {
  def strong = s"<strong>$original</strong>" 
  def em = s"<em>$original</em>"
}

object Example extends App {
	implicit def pimpMyString(original:String) = new StringPimp(original)
	
	println("Hello World".strong.em)
}