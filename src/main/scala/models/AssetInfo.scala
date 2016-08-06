package models

object AssetInfo extends Enumeration {
  type AssetInfo = Value
  val All = Value("info")
  val Leverage = Value("leverage")
  val Fees = Value("fees")
  val Margin = Value("margin")
}