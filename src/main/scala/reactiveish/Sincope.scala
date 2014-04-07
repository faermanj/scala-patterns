package reactiveish

import scala.io.Source._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
// Async / Await is a library
// https://github.com/scala/async
import scala.async.Async._ 

object Sincope extends App {
  def read(url: String) = fromURL(url, "UTF-8").mkString.take(80)

  val qconsp = Future { 
    println("Going to a nice conference will take a while!")
    read("http://qconsp.com")
  }
  
  //See that futures start immediatly
  Thread.sleep(2000)
  
  val futuro = async {
    val s = await(qconsp) //does not actually await
    println(s)
  }
  
  println("== Async results below ==")
  Await.ready(futuro, 10 seconds)
}