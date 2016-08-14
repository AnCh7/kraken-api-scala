package models

// "XDAOXETH": [[1470482497, "0.00999000", "0.01005000"]]
case class Spread(time: Double, bid: Double, ask: Double)

object Spread {
  def apply(data: Seq[String]): Spread = {
    Spread(data(0).toDouble, data(1).toDouble, data(2).toDouble)
  }
}
