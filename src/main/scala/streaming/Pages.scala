package streaming
import math._

case class Page(number: Int)

case class Pages(pages: Seq[Page], truncated: Boolean)

class PagesService(pageSize: Int, pagesServed: Int) {
  def getPages =
    Pages((1 to pageSize).map(Page), pageSize < pagesServed)

  def nextPages(previous: Pages) = {
    val first = previous.pages.last.number + 1
    val last = min(first + pageSize, pagesServed)
    Pages((first to last).map(Page), last < pagesServed)
  }
}

object PagesClient extends App {
  val service = new PagesService(10, 100)

  val chapters: Stream[Pages] = service.getPages #::
    chapters
    .map(service.nextPages)
    .takeWhile(_.pages.nonEmpty)

  val book: Stream[Page] = chapters.flatten(_.pages)

  book take 50 foreach { println(_) }
}
