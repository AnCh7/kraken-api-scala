package responses

import models.Asset

/*
{
  "error": [],
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
  * @param name  asset name.
  * @param asset array of asset info.
  */
case class AssetsResponse(name: String, asset: Asset)
