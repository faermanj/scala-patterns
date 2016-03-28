package syntax.scala

class TypeAliases {
  
  type Versao = Int
  type Significado  = String
  type Significados = Map[Versao,Significado]
  type Palavra = String
  type Dicionario = Map[Palavra,Significados]
  
  val dics:Dicionario = Map()
  def lookup(palavra:Palavra,versao:Versao) = 
    dics.get(palavra)
        .flatMap{_.get(versao)}
        .getOrElse(throw new IllegalArgumentException)
}
