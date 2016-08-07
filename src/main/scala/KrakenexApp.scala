import dataaccess.{KrakenDao, KrakenQueries}
import restapi.KrakenApi

object KrakenexApp extends App {

  import doobie.imports._
  import scalaz._, Scalaz._
  import scalaz.concurrent.Task
  import scalaz.stream.Process

  override def main(args: Array[String]): Unit = {

    //    val result = KrakenApi.getAssetInfo()
    //    result match {
    //      case Left(errors) => print(errors)
    //      case Right(asset) => asset.foreach(a => KrakenDao.insertAsset(a))
    //    }
  }
}