package responses

/*
{
  "error":[],
  "result": {
    "unixtime": 1470466401,
    "rfc1123": "Sat,  6 Aug 16 06:53:21 +0000"
  }
}
*/
/**
  * @param unixtime unix timestamp.
  * @param rfc1123  RFC 1123 time format.
  */
case class TimeResponse(unixtime: Double, rfc1123: String)
