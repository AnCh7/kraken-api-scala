package models

object OrderType extends Enumeration {
  type OrderType = Value
  val Buy = Value("m")
  val Sell = Value("l")
}
