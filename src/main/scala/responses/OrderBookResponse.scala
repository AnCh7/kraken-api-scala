package responses

import models.OrderBook

/*
{
  "error": [],
  "result": {
    "XDAOXETH": {
    "asks": [["0.01005000", "107497.597", 1470485533]],
    "bids": [["0.01000000", "4717.878", 1470485462]]
  }
  }
}
 */
/**
  * @param name pair name.
  */
case class OrderBookResponse(name: String, result: OrderBook)
