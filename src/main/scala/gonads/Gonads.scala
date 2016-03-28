package gonads

import scala.util.Try
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import java.text.SimpleDateFormat
import java.util.Date
import scala.util.Random._
  
object Gonads extends App {
  
  def triplo(i: Integer) = i * 3
  
  def triploOuNada(i: Integer):Integer = 
    if (i % 2 == 0) triplo(i) 
    else null
  
  def triploOuFalha(i: Integer):Integer = 
    if (i % 3 == 0) triplo(i) 
    else throw new Exception("Ops")
  
  def triploOuDemora(i: Integer): Integer = {
    if (i % 4 != 0) Thread.sleep(2000)
    triplo(i)
  }
  
  Option{ triploOuNada(2) }.map(triplo).foreach(println)
  Option{ triploOuNada(3) }.map(triplo).foreach(println)
  
  //Lidando com falhas 
  
                         
  Try{ triploOuFalha(3) }.map(triplo).foreach(println)
  Try{ triploOuFalha(4) }.map(triplo).foreach(println)

  //Lidando com latencia  
  

  val sdf = new SimpleDateFormat("HH:mm:ss")
  def now = sdf.format(new Date)
  
  Future{ triploOuDemora(4) }.map(triplo).foreach(i => println(s"${i} @ ${now}"))
  Future{ triploOuDemora(5) }.map(triplo).foreach(i => println(s"${i} @ ${now}"))

  Thread.sleep(3000)                      
  
  val rands = Seq.fill(12){nextInt(100)}
  
  for {
    r <- rands
    o <- Option { triploOuNada(r) }
    t <- Try    { triploOuFalha(o) }
    f <- Future { triploOuDemora(t) }
  } println(s"${f} @ ${now} !" )
                              

  Thread.sleep(5000)
                                                  
}