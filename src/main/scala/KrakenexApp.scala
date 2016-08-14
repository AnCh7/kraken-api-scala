import dataaccess.AssetDao
import models.Asset
import restapi.KrakenApi

object KrakenexApp extends App {

  override def main(args: Array[String]): Unit = {

    val response = new KrakenApi().getAssetInfo("info", "currency", "DAO")
    response match {
      case Left(errors) => println(errors.mkString("/"))
      case Right(data) => {
        AssetDao.dropTable()
        AssetDao.createTable()
        val asset = data.head.result
        println(asset)
        println(AssetDao.insert(asset))
        println(AssetDao.findByAClass("currency"))
        println(AssetDao.findByAltName("DAO"))
        println(AssetDao.update(1, Asset("currency", "NEWDAO", 10, 5)))
        println(AssetDao.delete(1))
      }
    }
  }
}