package models

/*
{
  "XDAOXETH": {
    "asks": [["0.01005000", "107497.597", 1470485533]],
    "bids": [["0.01000000", "4717.878", 1470485462]]
  }
}
 */
/**
  *
  * @param asks ask side array of array entries (price, volume, timestamp)
  * @param bids bid side array of array entries (price, volume, timestamp)
  */
case class OrderBook(asks: List[PriceLevel], bids: List[PriceLevel])

