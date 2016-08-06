package models

object Interval extends Enumeration {
  type Interval = Value
  val `1` = Value(1)
  val `5` = Value(5)
  val `15` = Value(15)
  val `30` = Value(30)
  val `60` = Value(60)
  val `240` = Value(240)
  val `1440` = Value(1440)
  val `10080` = Value(10080)
  val `21600` = Value(21600)
}
