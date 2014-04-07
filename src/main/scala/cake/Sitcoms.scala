package cake

trait InterjectionComponent {
  def interjector: Interjector

  trait Interjector {
    def interjection: String
  }
}

trait BigBangTheory extends InterjectionComponent {
  def interjector = Sheldon

  object Sheldon extends Interjector {
    def interjection = "BAZINGA"
  }
}

trait Simpsons extends InterjectionComponent {
  def interjector = Homer

  object Homer extends Interjector {
    def interjection = "DUH"
  }
}

trait Sitcom {
  def play
}

trait SimpleSitcom extends Sitcom {
  this: InterjectionComponent => /* this self type is required to be mixed-in */

  def play = println( s"${interjector.interjection}! Ahauhuauhahu!")
}

object Sitcoms extends App {
  val sony = new SimpleSitcom with BigBangTheory
  val fox = new SimpleSitcom with Simpsons
  sony.play
  fox.play
}