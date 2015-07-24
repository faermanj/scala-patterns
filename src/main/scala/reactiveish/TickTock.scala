package reactiveish

import rx.lang.scala._
import rx.lang.scala.schedulers._
import scala.concurrent.duration._
import scala.concurrent.Await
import java.util.Date

object TickTock extends App {
  val freq = 500
  val ticks: Observable[Long] = Observable.interval(freq millis)

  val sub = ticks
    .map { _ * freq }
    .filter { _ % 1000 == 0 }
    //.buffer(3,2)
    .subscribe { tick => println(f"${new Date}%tT ${tick}%s") }

  Thread.sleep(20 * freq)
  sub.unsubscribe
  Thread.sleep(2 * freq)
  println("Time for lunch!")
}