package syntax.scala

import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.NodeList
import org.w3c.dom.Node

import java.util

object DOMLegacy extends App {
  val xhtml = """<html>
    <h1> Oi </h1>
    <h1> Mundo </h1>
  </html>"""
  val is = new ByteArrayInputStream(xhtml.getBytes)
  val doc = DocumentBuilderFactory
              .newInstance
              .newDocumentBuilder
              .parse(is)
  val nodes:NodeList = doc.getElementsByTagName("h1")
	//Nada que uma conversao implicita nao resolva  
  implicit  class TraversableNodeList(nl: NodeList)
    extends Traversable[Node] {

    def iterator: Iterator[org.w3c.dom.Node] = ???

    override def foreach[U](f: Node => U): Unit =
      for (i <- 0 until nl.getLength) f(nl.item(i))
  }

  /*
  for (node <- nodes;
       content <- node.getTextContent)
    println(content)

   */
}
