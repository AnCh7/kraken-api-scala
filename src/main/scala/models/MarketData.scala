package models

// "XDAOXETH": [[1470432900, "0.01006", "0.01006", "0.01006", "0.01006", "0.00000", "0.00000000", 0]]
case class MarketData(time: Int, open: Double, high: Double, low: Double, close: Double, vwap: Double, volume: Double, count: Int)

