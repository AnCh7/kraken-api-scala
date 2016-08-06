package models

/*
{
  "result": {
    "XETH": {
    "aclass": "currency",
    "altname": "ETH",
    "decimals": 10,
    "display_decimals": 5
  }
  }
}
*/
/**
  * @param aclass           asset class.
  * @param altname          alternate name
  * @param decimals         scaling decimal places for record keeping.
  * @param display_decimals scaling decimal places for output display.
  */
case class Asset(aclass: String, altname: String, decimals: Double, display_decimals: Double)



