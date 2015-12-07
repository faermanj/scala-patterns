/**
*     Scala em 10m
*
*		Julio M. Faerman
*   	@jmfaerman
*
* - Por que aprender Scala?
* - Três melhorias ao Java
*  1- Linguagem Enxuta
*  2- Reuso e Melhoria de APIs
*  3- Programação Funcional
*
* Codigo em http://bit.ly/scalaem10m
**/

import java.text._
import java.util._
import javax.xml.parsers._
import org.w3c.dom._
import java.io._

object ScalaEm10m {
  println("Scala em 10m")                         //> Scala em 10m

  //1. Linguagem Enxuta
  
  class Pessoa(nome: String) {
  	/*
  		var nome
  	  def this(nome:String) = {
  			this.nome = nome
  		}
  	*/
  	
    /*public*/ def saudacao /*():String throws Exception*/ =
      //{
      //var result:String
      if (nome.length() < 10)
      	//Interpolacao de String
        /*result =*/ s"oi $nome!"
      else throw new Exception("Nome Muito Longo")
    //return result
    //}
  }

  //Inferencia de tipos
  val fulano = new Pessoa("Fulano")               //> fulano  : ScalaEm10m.Pessoa = ScalaEm10m$$anonfun$main$1$Pessoa$1@7f63425a
	println(fulano.saudacao)                  //> oi Fulano!

  //2. Reuso e Melhoria
  
  // Melhorando a classe String
  implicit class StringMelhor(str: String) {
    def enfatize = s"<em>$str</em>"
  }

  println("Uau!!!".enfatize)                      //> <em>Uau!!!</em>

  // NodeList vs List<Node>?
  def xhtml = """
  	<html>
  			<div>um</div>
  			<div>dois</div>
  			<div>tres</div>
  	</html>
  	"""                                       //> xhtml: => String
  	
  val doc = DocumentBuilderFactory
  	.newInstance
  	.newDocumentBuilder
  	.parse(new ByteArrayInputStream(xhtml.getBytes))
                                                  //> doc  : org.w3c.dom.Document = [#document: null]

  val nodeList:NodeList = doc.getElementsByTagName("div")
                                                  //> nodeList  : org.w3c.dom.NodeList = com.sun.org.apache.xerces.internal.dom.D
                                                  //| eepNodeListImpl@2d6e8792

  implicit class TraversableNodeList(nl: NodeList) extends Traversable[Node] {
    override def foreach[U](f: Node => U): Unit =
      for (i <- 0 until nl.getLength) f(nl.item(i))
  }

  for (node <- nodeList) println(node.getTextContent())
                                                  //> um
                                                  //| dois
                                                  //| tres

  //3. Programacao Funcional

  //Collections e Closures
  nodeList
    .map { _.getTextContent }
    .filter { _.length > 3 }
    .foreach { println }                          //> dois
                                                  //| tres
	val sdf = new SimpleDateFormat("HH:mm:ss")//> sdf  : java.text.SimpleDateFormat = java.text.SimpleDateFormat@8140d380
  def now = sdf.format(new Date)                  //> now: => String
  def imprime(s:Any) = println(s"${s} @ ${now}")  //> imprime: (s: Any)Unit
  
  def treta(i: Integer) =
  	if (i == 2)
  		null
  	else if (i == 3)
  		throw new Exception()
  	else if (i == 4) {
  		Thread.sleep(2000)
  		i
  	} else i                                  //> treta: (i: Integer)Integer
  
  // Lidando com null
	val o1 = Option{ treta(1) }               //> o1  : Option[Integer] = Some(1)
	val o2 = Option{ treta(2) }               //> o2  : Option[Integer] = None
	
  o1.map(_ * 2).foreach(imprime)                  //> 2 @ 20:11:50
  o2.map(_ * 2).foreach(imprime)

  // Lidando com falha
  import scala.util.Try

  Try{ treta(1) }.map(_ * 2).foreach(imprime)     //> 2 @ 20:11:50
  Try{ treta(3) }.map(_ * 2).foreach(imprime)

  // Lidando com latencia
  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global
    
	Future{ treta(1) }.map(_ * 2).foreach(imprime)
	Future{ treta(4) }.map(_ * 2).foreach(imprime)
                                                  //> 2 @ 20:11:50

  Thread.sleep(5000)                              //> 8 @ 20:11:52
  
	//  Alguma semelhanca?
	Option { treta(2)  }.map(_ * 2).foreach(imprime)
	Try    { treta(3)  }.map(_ * 2).foreach(imprime)
	Future { treta(4)  }.map(_ * 2).foreach(imprime)
	
	Thread.sleep(3000)                        //> 8 @ 20:11:57
	
	 
	def rand = new Random().nextInt(3)+1      //> rand: => Int
	
	val itof = Future(Option(Try(treta(rand))))
                                                  //> itof  : scala.concurrent.Future[Option[scala.util.Try[Integer]]] = scala.co
                                                  //| ncurrent.impl.Promise$DefaultPromise@29ca901e

	for (ito <- itof;
			 it <- ito;
			 i <- it)
		imprime(i)

	Thread.sleep(5000)                        //> 1 @ 20:11:58-
}
// Obrigado! Perguntas?
// Codigo em http://bit.ly/scalaem10m
//
// Julio M. Faerman - @jmfaerman
//   ___________________
//  < Conheça a Eduvem  >
//  < http://eduvem.com >
//   -------------------
//          \   ^__^
//           \  (oo)\_______
//              (__)\       )\/\
//                  ||----w |
//                  ||     ||
//