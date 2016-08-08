package responses

import models.Trade

/*
{
  "error": [],
  "result": {
    "XDAOXETH": [["0.00999000", "1831.57179993", 1470350817.5335, "s", "l", ""],
                 ["0.01008000", "535.47589286", 1470350824.6818, "b", "l", ""]],
    "last": "1470486295862812005"
  }
}
 */
/**
  * @param pair_name pair name.
  *                  Array of array entries(price, volume, time, buy/sell, market/limit, miscellaneous).
  * @param last      id to be used as since when polling for new trade data
  */
case class TradesResponse(pair_name: Seq[Trade], last: String)


