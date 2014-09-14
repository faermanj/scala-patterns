

object qconrj14 {
  println("Scala em 20m no QCON Rio")             //> Scala em 20m no QCON Rio

  //1. Seja menos prolixo
  class Pessoa(nome: String) { //<- Isso e um contstrutor
    /*private*/ def saudacao /*():String throws Exception*/ =
      //{
      //var result:String
      if (nome.length() < 10)
        /*result =*/ s"oi $nome!"
      else throw new Exception("Nome Muito Longo")
    //return result
    //}

    def digaOi = println(saudacao)
  }

  //1.1 Inferencia de tipos
  val fulano = new Pessoa("Fulano")               //> fulano  : qconrj14.Pessoa = qconrj14$$anonfun$main$1$Pessoa$1@3498ed
  fulano.digaOi                                   //> oi Fulano!

  //1.2 Princípio do acesso uniforme
  println(fulano.saudacao)                        //> oi Fulano!

  //1.3 Mutabilidade explicita
  var ciclano = new Pessoa("Ciclano")             //> ciclano  : qconrj14.Pessoa = qconrj14$$anonfun$main$1$Pessoa$1@180bc464
  ciclano = fulano
  //fulano = ciclano

  //2. Melhore APIs existentes e o codigo legado
  implicit class StringMelhor(str: String) {
  	//1.4 String Interpolation
    def tagueie(tag: String) = s"<$tag>$str</$tag>"
    def enfatize = tagueie("em")
  }

  println("Uau!!!".enfatize)                      //> <em>Uau!!!</em>

  //2.1 "Compativel" com bibliotecas Java
  import javax.xml.parsers._
  import org.w3c.dom._
  import java.io._

  def data = new ByteArrayInputStream(
    //1.5 Strings de varias linhas
    """
  	<html>
  			<div>um</div>
  			<div>dois</div>
  			<div>tres</div>
  	</html>
  	""".getBytes)                             //> data: => java.io.ByteArrayInputStream
  val doc = DocumentBuilderFactory
  	.newInstance
  	.newDocumentBuilder
  	.parse(data)                              //> doc  : org.w3c.dom.Document = [#document: null]
  	  

  //2.2 As "palavras chave" te entendem também
  val nodeList:NodeList = doc.getElementsByTagName("div")
                                                  //> nodeList  : org.w3c.dom.NodeList = com.sun.org.apache.xerces.internal.dom.D
                                                  //| eepNodeListImpl@1324409e
  
  implicit class TraversableNodeList(nl: NodeList) extends Traversable[Node] {
    override def foreach[U](f: Node => U): Unit =
      for (i <- 0 until nl.getLength) f(nl.item(i))
  }

  for (node <- nodeList) println(node.getTextContent())
                                                  //> um
                                                  //| dois
                                                  //| tres

  //3. É funcional, mas e dai?

  //3.1 Collections e Closures
  nodeList
    .map { _.getTextContent }
    .filter { _.length > 3 }
    .foreach { println }                          //> dois
                                                  //| tres

  //3.2 Funções como argumentos e retornos
  implicit class InputExtreme(in: InputStream) {
    def aplica[T](f: InputStream => T): T =
      try f(in)
      finally in.close
  }

  import scala.io.Source
  def destagueia(in: InputStream) = Source
    .fromInputStream(in)
    .mkString
    .replaceAll("<", "[")
    .replaceAll(">", "]")                         //> destagueia: (in: java.io.InputStream)String

  data.aplica(destagueia)                         //> res0: String = "
                                                  //|   	[html]
                                                  //|   			[div]um[/div]
                                                  //|   			[div]dois[/div]
                                                  //|   			[div]tres[/div]
                                                  //|   	[/html]
                                                  //|   	"

  //3.3 Lidando com null
  def triplo(i: Integer) = i * 3                  //> triplo: (i: Integer)Int
  def triploOuNada(i: Int): Integer = if (i % 2 == 0) triplo(i) else null
                                                  //> triploOuNada: (i: Int)Integer

  Option{ triploOuNada(2) }.map(triplo).foreach(println)
                                                  //> 18
  Option{ triploOuNada(3) }.map(triplo).foreach(println)

  //3.4 Lidando com falha
  import scala.util.Try

  def triploOuFalha(i: Integer): Integer = if (i % 3 == 0) triplo(i) else throw new Exception("Ops")
                                                  //> triploOuFalha: (i: Integer)Integer
  Try{ triploOuFalha(3) }.map(triplo).foreach(println)
                                                  //> 27
  Try{ triploOuFalha(4) }.map(triplo).foreach(println)

  //3.5 Lidando com latencia
  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global
  
  def triploOuDemora(i: Integer): Integer = if (i % 4 == 0)
    triplo(i)
  else {
    Thread.sleep(2000)
    i
  }                                               //> triploOuDemora: (i: Integer)Integer
  
  import java.text.SimpleDateFormat
  import java.util.Date
  
  val sdf = new SimpleDateFormat("HH:mm:ss")      //> sdf  : java.text.SimpleDateFormat = java.text.SimpleDateFormat@8140d380
  def now = sdf.format(new Date)                  //> now: => String
  
	Future{ triploOuDemora(4) }.map(triplo).foreach(i => println(s"${i} @ ${now}"))
	Future{ triploOuDemora(5) }.map(triplo).foreach(i => println(s"${i} @ ${now}"))
                                                  //> 36 @ 18:12:15
  Thread.sleep(3000)                              //> 15 @ 18:12:17
	
	//3.666. Lidando com M* :)
	//	Xxxxx{ triploOuXxxx(0) }.map(triplo).foreach(println)
	
	
	import scala.util.Random._
	val rands = Seq.fill(12){nextInt(100)}    //> rands  : Seq[Int] = List(24, 20, 45, 62, 99, 59, 11, 38, 58, 92, 91, 75)
	for {
		q <- rands
		c <- Option { triploOuNada(q) }
		o <- Try { triploOuFalha(c) }
		n <- Future { triploOuDemora(o) }
	} println(s"${n} @ ${now}" )              //> 648 @ 18:12:18
                                                  //| 540 @ 18:12:18

	Thread.sleep(5000)                        //> 2484 @ 18:12:18
                                                  //| 558 @ 18:12:20
                                                  //| 342 @ 18:12:20
                                                  //| 522 @ 18:12:20-

  // Codigo em http://...
}