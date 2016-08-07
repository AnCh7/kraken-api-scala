package models

// "XDAOXETH": [[1470432900, "0.01006", "0.01006", "0.01006", "0.01006", "0.00000", "0.00000000", 0]]
case class MarketData(time: Int, open: Double, high: Double, low: Double, close: Double, vwap: Double, volume: Double, count: Int)

object MarketData {
  def apply(data: List[Object]): MarketData = {
    MarketData(data.head.toString.toInt,
      data(1).toString.toDouble,
      data(2).toString.toDouble,
      data(3).toString.toDouble,
      data(4).toString.toDouble,
      data(5).toString.toDouble,
      data(6).toString.toDouble,
      data(7).toString.toInt)
  }
}

