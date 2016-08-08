package common

import scala.util.{Failure, Success, Try}

object Helper {

  @annotation.tailrec
  def retry[T](tries: Int)(function: => T): T = {
    Try {
      function
    } match {
      case Success(x) => x
      case _ if tries > 1 => retry(tries - 1)(function)
      case Failure(e) => throw e
    }
  }
}
