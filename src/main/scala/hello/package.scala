package object hello {
  val o = 'o
  implicit class Emoticons(s:Symbol) {
    def ▽(s:Symbol) = println("Não faça isso em casa!")
  }
  def ____ = None  
}