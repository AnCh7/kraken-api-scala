package models

/*
{
  "result": {
    "XDAOXETH": [[1470432900,
                  "0.01006",
                  "0.01006",
                  "0.01006",
                  "0.01006",
                  "0.00000",
                  "0.00000000",
                  0]]
  }
}
 */
case class MarketData(time: Double, open: String, high: String, low: String,
                      close: String, vwap: String, volume: String, count: Double)

