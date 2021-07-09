package syntax.scala
import scala.language.postfixOps

object Bicho extends App{

  trait Nomeado {
    var name: String = _

    def comNome(name: String): this.type = {
      this.name = name
      this
    }
  }
  
  class Pessoa extends Nomeado {
    def oi = println(s"Oi $name")
  }
  
  new Pessoa() comNome("Julio") oi 
}