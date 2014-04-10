package reactiveish

import scala.io.Source._
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object SomeDay extends App {
  def read(url: String) = fromURL(url, "UTF-8") mkString
 
  val qconsp = Future { 
    println("Going to a nice conference will take a while!")
    read("http://qconsp.com")
  }
  println("The future has begun!")
  Thread.sleep(2000)
  
  println("Let us check results")
  qconsp onSuccess {
    case site => println(s"Once: ${site.substring(0, 50)}")
  }
  
  //And cache results
  qconsp onSuccess {
    case site => println(s"Again: ${site.substring(0, 50)}")
  }

  Await.ready(qconsp, 10 seconds)
}