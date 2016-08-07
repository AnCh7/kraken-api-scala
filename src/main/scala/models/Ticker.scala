package models

/*
{
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
*/
/**
  *
  * @param a ask array (price, whole lot volume, lot volume).
  * @param b bid array (price, whole lot volume, lot volume).
  * @param c last trade closed array (price, lot volume).
  * @param v volume array (today, last 24 hours).
  * @param p volume weighted average price array (today, last 24 hours).
  * @param t number of trades array (today, last 24 hours).
  * @param l low array (today, last 24 hours).
  * @param h high array (today, last 24 hours).
  * @param o today's opening price.
  * @note today's prices start at 00:00:00 UTC.
  */
case class Ticker(a: Array[Double], b: Array[Double], c: Array[Double], v: Array[Double],
                  p: Array[Double], t: Array[Int], l: Array[Double], h: Array[Double], o: Double)
