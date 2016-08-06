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
  * @param name asset name.
  */
case class AssetsResponse(name: Asset)
