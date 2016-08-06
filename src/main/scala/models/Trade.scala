package models

/*
{
  "XDAOXETH": [["0.00999000", "1831.57179993", 1470350817.5335, "s", "l", ""],
               ["0.01008000", "535.47589286", 1470350824.6818, "b", "l", ""]],
 "last": "1470486295862812005"
}
 */
case class Trade(price: String,
                 volume: String,
                 time: Double,
                 `buy/sell`: String,
                 `market/limit`: String,
                 miscellaneous: String)
