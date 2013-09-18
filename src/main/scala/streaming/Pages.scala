package streaming
import math._

case class Page(number: Int)

case class Pages(pages: Seq[Page], truncated: Boolean)

class PagesService(pageSize: Int, pagesServed: Int) {
  def toPage(i: Int) = new Page(i)

  def getPages =
    Pages((1 to pageSize).map(toPage), pageSize < pagesServed)

  def nextPages(previous: Pages) = {
    val first = previous.pages.last.number + 1
    val last = min(first + pageSize, pagesServed)
    Pages((first to last).map(toPage), last < pagesServed)
  }
}

object PagesClient extends App {
	val service = new PagesService(10,100)
	
	val first = service.getPages
	assert(first.truncated)
	first.pages.foreach(println(_))
	
	val second = service.nextPages(first)
	second.pages.foreach(println(_))
	
	val book:Stream[Page] = ???
}
