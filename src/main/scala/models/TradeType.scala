package models

object TradeType extends Enumeration {
  type TradeType = Value
  val Market = Value("b")
  val Limit = Value("s")
}
