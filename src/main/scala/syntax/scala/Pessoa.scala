package syntax.scala

class Pessoa(val nome:String) {
  def saudacao = if (nome.length < 10)
    s"Oi $nome!"
  else throw new Exception("Nome muito longo")
}

object Pessoa extends App{
  val p = new Pessoa("Fulano")
  println(p.saudacao)
  println(p.nome)
}