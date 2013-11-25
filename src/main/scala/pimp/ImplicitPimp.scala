package pimp

object ImplicitPimp extends App {
  implicit class StringPimp(original: String) {
    def strong = s"<strong>$original</strong>"
    def em = s"<em>$original</em>"
  }
  println("Hello World".strong.em)
}