package reactiveish

import scala.util._
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.concurrent.duration._

object SomeDay extends App {
  def read(url: String) = {
    Thread.sleep(1000)
    url
  }

  val qconsp = Future { 
    println("Going to a nice conference will take a while!")
    read("http://qconsp.com")
  }
  println("The future has begun!")
  Thread.sleep(2000)
  
  println("Let us check results")
  qconsp.onComplete {
    case Success(site) => println(s"Once: ${site.substring(0, 50)}")
    case Failure(exception) => exception.printStackTrace()
  }
  
  //And cache results
  qconsp.onComplete {
    case Success(site) => println(s"Again: ${site.substring(0, 50)}")
    case Failure(exception) => exception.printStackTrace()
  }

  Await.ready(qconsp, 10 seconds)
}