package responses

import models.Ticker

/*
{
  "error": [],
  "result": {
    "XDAOXETH": {
    "a": ["0.01005000", "129646","129646.000"],
    "b": ["0.00999000","71701","71701.000"],
    "c": ["0.01005000","1001.00100100"],
    "v": ["124415.57950624","602833.87427270"],
    "p": ["0.01002067","0.01001079"],
    "t": [55,230],
    "l": ["0.00999000","0.00999000"],
    "h": ["0.01006000","0.01007000"],
    "o": "0.01005000"
  }
  }
}
 */
/**
  * @param name   pair name.
  * @param result pair info.
  */
case class TickerResponse(name: String, result: Ticker)
