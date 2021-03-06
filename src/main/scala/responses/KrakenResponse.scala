package responses

/*
{
  "error":["EGeneral:Unknown method"],
  "result":{}
}
*/
/**
  * @param error  array of error messages.
  * @param result json response.
  * @tparam A type of response.
  */
case class KrakenResponse[A](error: Seq[String], result: A)