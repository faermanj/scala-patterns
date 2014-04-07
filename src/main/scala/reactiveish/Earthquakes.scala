package reactiveish

import rx.lang.scala._
import scala.util.Random
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
object Earthquakes extends App {
  type GeoCoordinate = Tuple2[Double, Double]

  case class Earthquake(magnitude: Double, location: GeoCoordinate)

  def usgs: Observable[Earthquake] = Observable { observer =>
    {
      while (true) {
        val freq = 1500
        val rand: Double = Random.nextInt(freq)
        val quake = Earthquake((rand / freq), (rand % 3, (rand % 3) + 1))
        observer.onNext(quake)
        Thread.sleep(500 + rand.round)
      }
    }
  }

  object Magnitude extends Enumeration {
    def apply(magnitude: Double): Magnitude = magnitude match {
      case x if (x < 0.15) => Micro
      case x if (x < 0.30) => Minor
      case x if (x < 0.45) => Light
      case x if (x < 0.60) => Moderate
      case x if (x < 0.75) => Strong
      case x if (x < 0.90) => Major
      case _ => Great
    }

    type Magnitude = Value
    val Micro, Minor, Light, Moderate, Strong, Major, Great = Value
  }

  object Country extends Enumeration {
    type Country = Value
    val Aral, Dudkinka, Vladivostok = Value
  }

  val quakes = usgs

  val decent = quakes
    .map { q => (q.location, Magnitude(q.magnitude)) }
    .filter { case (loc, mag) => mag >= Magnitude.Moderate }

  //decent.subscribe { println(_) }

  def reverseGeocode(c: GeoCoordinate): Future[Country.Value] = Future {
    Thread.sleep(Random.nextInt(1000))
    c match {
      case (1.0, 2.0) => Country.Aral
      case (2.0, 3.0) => Country.Dudkinka
      case _ => Country.Vladivostok
    }
  }

  val withCountry: Observable[Observable[(Magnitude.Value, Country.Value)]] = {
    quakes.map { quake => {
        val country: Future[Country.Value] = reverseGeocode(quake.location)       
        Observable.from(country.map(country => (Magnitude(quake.magnitude), country)))
      }
    }
  }
  
  withCountry.concat.subscribe { println(_) }

}