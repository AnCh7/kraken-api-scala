package models

// "asks": [["0.01005000", "107497.597", 1470485533]] or "bids": [["0.01000000", "4717.878", 1470485462]]
case class PriceLevel(price: Double, volume: Double, timestamp: Int)
