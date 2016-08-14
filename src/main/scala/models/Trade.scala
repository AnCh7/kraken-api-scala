package models

import models.OrderType.OrderType
import models.TradeType.TradeType

/*
{
  "XDAOXETH": [["0.00999000", "1831.57179993", 1470350817.5335, "s", "l", ""],
               ["0.01008000", "535.47589286", 1470350824.6818, "b", "l", ""]],
 "last": "1470486295862812005"
}
 */
case class Trade(price: Double,
                 volume: Double,
                 time: Double,
                 TradeType: TradeType,
                 OrderType: OrderType,
                 miscellaneous: String)

object Trade {
  def apply(data: Seq[String]): Trade = {
    Trade(data(0).toDouble, data(1).toDouble, data(2).toDouble, TradeType.withName(data(3)), OrderType.withName(data(4)), data(5))
  }
}
