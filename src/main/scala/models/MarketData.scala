package models

// "XDAOXETH": [[1470432900, "0.01006", "0.01006", "0.01006", "0.01006", "0.00000", "0.00000000", 0]]
case class MarketData(time: Int, open: Double, high: Double, low: Double, close: Double, vwap: Double, volume: Double, count: Int)

object MarketData {
  def apply(data: Seq[String]): MarketData = {
    MarketData(data.head.toString.toInt, data(1).toDouble, data(2).toDouble,
      data(3).toDouble, data(4).toDouble, data(5).toDouble, data(6).toDouble, data(7).toInt)
  }
}

