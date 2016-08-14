package responses

import models.MarketData

/*
{
  "error": [],
  "result": {
    "XDAOXETH": [[1470432900, "0.01006", "0.01006", "0.01006", "0.01006", "0.00000", "0.00000000", 0]],
    "last": 1470475980
  }
}
 */
/**
  *
  * @param name pair name.
  * @param result Array of array entries(time, open, high, low, close, vwap, volume, count).
  * @param last id to be used as since when polling for new, committed OHLC data.
  * @note the last entry in the OHLC array is for the current, not-yet-committed frame and will always be present,
  *       regardless of the value of "since".
  */
case class OhlcResponse(name: String, result: Seq[MarketData], last: Int)