package models

/*
{
  "result": {
    "XDAOXETH": {
    "a": ["0.01005000", "21510", "21510.000"],
    "b": ["0.00999000", "161072", "161072.000"],
    "c": ["0.01005000", "14.92492492"],
    "v": ["101712.50017495", "592165.63850610"],
    "p": ["0.01002631", "0.01001112"],
    "t": [37, 217],
    "l": ["0.00999000", "0.00999000"],
    "h": ["0.01006000", "0.01007000"],
    "o": "0.01005000"
  }
  }
}
*/
/**
  *
  * @param ask    ask array (price, whole lot volume, lot volume).
  * @param bid    bid array (price, whole lot volume, lot volume).
  * @param close  last trade closed array (price, lot volume).
  * @param volume volume array (today, last 24 hours).
  * @param price  volume weighted average price array (today, last 24 hours).
  * @param trades number of trades array (today, last 24 hours).
  * @param low    low array (today, last 24 hours).
  * @param high   high array (today, last 24 hours).
  * @param open   today's opening price.
  * @note today's prices start at 00:00:00 UTC.
  */
case class Ticker(ask: List[String], bid: List[String], close: List[String], volume: List[String],
                  price: List[String], trades: List[Double], low: List[String], high: List[String], open: String)