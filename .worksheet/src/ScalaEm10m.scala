import org.w3c.dom.NodeList
import java.io.InputStream

/**
*		Julio M. Faerman
*   	@jmfaerman
*
* - Por que aprender Scala?
* - Três funcionalidades chave
*  1 Linguagem Enxuta
*  2 Reuso e Melhoria
*  3 Programação Funcional
* - E dai?
*
* Codigo em http://bit.ly/scalaem10m
**/
object ScalaEm10m {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(347); 
  println("Scala em 10m no Speakers Weekend")

  //1. Linguagem Enxuta
  
  class Pessoa(nome: String) { //<- Isso e um contstrutor e um campo
    /*public*/ def saudacao /*():String throws Exception*/ =
      //{
      //var result:String
      if (nome.length() < 10)
      	//Interpolacao de String
        /*result =*/ s"oi $nome!"
      else throw new Exception("Nome Muito Longo")
    //return result
    //}
  };$skip(434); 

  //Inferencia de tipos
  val fulano = new Pessoa("Fulano");System.out.println("""fulano  : ScalaEm10m.Pessoa = """ + $show(fulano ));$skip(26); 
	println(fulano.saudacao)

  //2. Reuso e Melhoria
  implicit class StringMelhor(str: String) {
    def tagueie(tag: String) = s"<$tag>$str</$tag>"
    def enfatize = tagueie("em")
  };$skip(189); 

  println("Uau!!!".enfatize)

  //"Compativel" com bibliotecas Java
  import javax.xml.parsers._
  import org.w3c.dom._
  import java.io._;$skip(257); 

  def data = new ByteArrayInputStream(
    """
  	<html>
  			<div>um</div>
  			<div>dois</div>
  			<div>tres</div>
  	</html>
  	""".getBytes);System.out.println("""data: => java.io.ByteArrayInputStream""");$skip(93); 
  
  val doc = DocumentBuilderFactory
  	.newInstance
  	.newDocumentBuilder
  	.parse(data);System.out.println("""doc  : org.w3c.dom.Document = """ + $show(doc ));$skip(108); 
  	  

  //As "palavras chave" te entendem também
  val nodeList:NodeList = doc.getElementsByTagName("div")
  
  implicit class TraversableNodeList(nl: NodeList) extends Traversable[Node] {
    override def foreach[U](f: Node => U): Unit =
      for (i <- 0 until nl.getLength) f(nl.item(i))
  };System.out.println("""nodeList  : org.w3c.dom.NodeList = """ + $show(nodeList ));$skip(245); 

  for (node <- nodeList) println(node.getTextContent());$skip(153); 

  //3. Programacao Funcional

  //Collections e Closures
  nodeList
    .map { _.getTextContent }
    .filter { _.length > 3 }
    .foreach { println }

  //Funcoes como argumentos e retornos
  implicit class InputExtreme(in: InputStream) {
    def aplica[T](f: InputStream => T): T =
      try f(in)
      finally in.close
  }

  import scala.io.Source;$skip(336); 
  def destagueia(in: InputStream) = Source
    .fromInputStream(in)
    .mkString
    .replaceAll("<", "[")
    .replaceAll(">", "]");System.out.println("""destagueia: (in: java.io.InputStream)String""");$skip(27); val res$0 = 

  data.aplica(destagueia);System.out.println("""res0: String = """ + $show(res$0));$skip(55); 

  //Lidando com null
  def triplo(i: Integer) = i * 3;System.out.println("""triplo: (i: Integer)Int""");$skip(74); 
  def triploOuNada(i: Int): Integer = if (i % 2 == 0) triplo(i) else null;System.out.println("""triploOuNada: (i: Int)Integer""");$skip(58); 

  Option{ triploOuNada(2) }.map(triplo).foreach(println);$skip(57); 
  Option{ triploOuNada(3) }.map(triplo).foreach(println)

  //3.4 Lidando com falha
  import scala.util.Try;$skip(153); 

  def triploOuFalha(i: Integer): Integer = if (i % 3 == 0) triplo(i) else throw new Exception("Ops");System.out.println("""triploOuFalha: (i: Integer)Integer""");$skip(55); 
  Try{ triploOuFalha(3) }.map(triplo).foreach(println);$skip(55); 
  Try{ triploOuFalha(4) }.map(triplo).foreach(println)

  //3.5 Lidando com latencia
  import scala.concurrent._
  import scala.concurrent.ExecutionContext.Implicits.global;$skip(237); 
  
  def triploOuDemora(i: Integer): Integer = if (i % 4 == 0)
    triplo(i)
  else {
    Thread.sleep(2000)
    i
  }
  
  import java.text.SimpleDateFormat
  import java.util.Date;System.out.println("""triploOuDemora: (i: Integer)Integer""");$skip(111); 
  
  val sdf = new SimpleDateFormat("HH:mm:ss");System.out.println("""sdf  : java.text.SimpleDateFormat = """ + $show(sdf ));$skip(33); 
  def now = sdf.format(new Date);System.out.println("""now: => String""");$skip(84); 
  
	Future{ triploOuDemora(4) }.map(triplo).foreach(i => println(s"${i} @ ${now}"));$skip(81); 
	Future{ triploOuDemora(5) }.map(triplo).foreach(i => println(s"${i} @ ${now}"));$skip(21); 
  Thread.sleep(3000);$skip(85); 
	
	//  Alguma semelhanca?
	Option { triploOuNada(1)   }.map(triplo).foreach(println);$skip(59); 
	Try    { triploOuFalha(1)  }.map(triplo).foreach(println);$skip(59); 
	Future { triploOuDemora(1) }.map(triplo).foreach(println)
	 
	import scala.util.Random._;$skip(71); 
	val rands = Seq.fill(12){nextInt(100)};System.out.println("""rands  : Seq[Int] = """ + $show(rands ));$skip(152); 
	for {
		q <- rands
		c <- Option { triploOuNada(q) }
		o <- Try { triploOuFalha(c) }
		n <- Future { triploOuDemora(o) }
	} println(s"${n} @ ${now}" );$skip(21); 

	Thread.sleep(5000)}
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
