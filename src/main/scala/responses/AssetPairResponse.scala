package responses

import models.AssetPair

/*
{
  "error": [],
  "result": {
    "XDAOXETH": {
    "altname": "DAOETH",
    "aclass_base": "currency",
    "base": "XDAO",
    "aclass_quote": "currency",
    "quote": "XETH",
    "lot": "unit",
    "pair_decimals": 5,
    "lot_decimals": 8,
    "lot_multiplier": 1,
    "leverage_buy": [],
    "leverage_sell": [],
    "fees": [[0, 0.26]],
    "fees_maker": [[0, 0.16]],
    "fee_volume_currency": "ZUSD",
    "margin_call": 80,
    "margin_stop": 40
  }
  }
}
 */
/**
  * @param name pair name.
  */
case class AssetPairResponse(name: AssetPair)


