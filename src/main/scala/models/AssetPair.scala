package models

/*
{
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
    "fees": [[0,0.26],
            [50000,0.24],
            [100000,0.22],
            [250000,0.2],
            [500000,0.18],
            [1000000,0.16],
            [2500000,0.14],
            [5000000,0.12],
            [10000000,0.1]],
    "fees_maker": [[0,0.16],
                  [50000,0.14],
                  [100000,0.12],
                  [250000,0.1],
                  [500000,0.08],
                  [1000000,0.06],
                  [2500000,0.04],
                  [5000000,0.02],
                  [10000000,0]],
    "fee_volume_currency": "ZUSD",
    "margin_call": 80,
    "margin_stop": 40
  }
}
 */
/**
  * @param altname             alternate pair name.
  * @param aclass_base         asset class of base component.
  * @param base                asset id of base component.
  * @param aclass_quote        asset class of quote component.
  * @param quote               asset id of quote component.
  * @param lot                 volume lot size.
  * @param pair_decimals       scaling decimal places for pair.
  * @param lot_decimals        scaling decimal places for volume.
  * @param lot_multiplier      amount to multiply lot volume by to get currency volume.
  * @param leverage_buy        array of leverage amounts available when buying.
  * @param leverage_sell       array of leverage amounts available when selling.
  * @param fees                fee schedule array in [volume, percent fee] tuple.
  * @param fees_maker          maker fee schedule array in [volume, percent fee] tuple (if on maker/taker).
  * @param fee_volume_currency volume discount currency.
  * @param margin_call         margin call level.
  * @param margin_stop         stop-out/liquidation margin level.
  */
case class AssetPair(altname: String,
                     aclass_base: String,
                     base: String,
                     aclass_quote: String,
                     quote: String,
                     lot: String,
                     pair_decimals: Double,
                     lot_decimals: Double,
                     lot_multiplier: Double,
                     leverage_buy: Seq[String],
                     leverage_sell: Seq[String],
                     fees: Seq[Seq[Double]],
                     fees_maker: Seq[Seq[Double]],
                     fee_volume_currency: String,
                     margin_call: Double,
                     margin_stop: Double)


