package dataaccess

import doobie.imports._
import models.Asset

import scalaz._
import Scalaz._
import scalaz.concurrent.Task
import scalaz.stream.Process

object KrakenDao {

  val xa = DriverManagerTransactor[Task](
    "org.postgresql.Driver", "jdbc:postgresql:kraken", "postgres", "postgres"
  )

  import xa.yolo._

  def create(): Unit = KrakenQueries.create.quick.unsafePerformSync

  def drop(): Unit = KrakenQueries.drop.quick.unsafePerformSync

  def insertAsset(asset: Asset): Unit = {
    KrakenQueries.insertAsset(asset.aclass, asset.altname, asset.decimals, asset.display_decimals).quick.run
  }

  def insertAsset2(asset: Asset): Unit = {
    KrakenQueries.insertAsset2(asset.aclass, asset.altname, asset.decimals, asset.display_decimals).quick.run
  }

  def updateAsset(asset: Asset): Unit = {
    KrakenQueries.updateAsset(asset.aclass, asset.altname, asset.decimals, asset.display_decimals).quick.run
  }

  def updateAsset2(asset: Asset): Unit = {
    KrakenQueries.updateAsset2(asset.aclass, asset.altname, asset.decimals, asset.display_decimals).quick.run
  }

  def insertAssets(asset: List[Asset]): Unit = {
    KrakenQueries.insertAssets(asset).quick.run
  }

  def insertAssets2(asset: List[Asset]): Unit = {
    KrakenQueries.insertAssets2(asset).quick.run
  }

  def findAssetByClass(aclass: String): Option[Asset] = {
    val a = KrakenQueries.findAssetByClass(aclass).process
    val r = a.map(asset => Asset(asset._1, asset._2, asset._3, asset._4))
    None
  }
}

object KrakenQueries {

  def create: Update0 =
    sql"""CREATE TABLE asset (
          asset_id           SERIAL    NOT NULL PRIMARY KEY,
          aclass             VARCHAR   NOT NULL,
          altname            VARCHAR   NOT NULL,
          decimals           INTEGER     NOT NULL,
          display_decimals   INTEGER     NOT NULL);
        """.update

  def drop: Update0 = sql"""DROP TABLE IF EXISTS asset""".update

  def insertAsset(aclass: String, altname: String, decimals: Int, display_decimals: Int): Update0 = {
    sql"""INSERT INTO asset (aclass, altname, decimals, display_decimals)
          VALUES ($aclass, $altname, $decimals, $display_decimals)""".update
  }

  def insertAsset2(aclass: String, altname: String, decimals: Int, display_decimals: Int): ConnectionIO[Asset] = {
    sql"""INSERT INTO asset (aclass, altname, decimals, display_decimals)
          VALUES ($aclass, $altname, $decimals, $display_decimals)""".update.withUniqueGeneratedKeys("id")
  }

  def updateAsset(aclass: String, altname: String, decimals: Int, display_decimals: Int): Update0 = {
    sql"""UPDATE asset
          SET aclass = $aclass,
              altname = $altname,
              decimals = $decimals,
              display_decimals = $display_decimals
          WHERE altname = altname""".update
  }

  def updateAsset2(aclass: String, altname: String, decimals: Int, display_decimals: Int): Process[ConnectionIO, Asset] = {
    sql"""UPDATE asset
          SET aclass = $aclass,
              altname = $altname,
              decimals = $decimals,
              display_decimals = $display_decimals
          WHERE altname = altname""".update.withGeneratedKeys[Asset]("asset_id")
  }

  def insertAssets(assets: List[Asset]): ConnectionIO[Int] = {
    val sql = "INSERT INTO asset (aclass, altname, decimals, display_decimals) VALUES (?, ?, ?, ?)"
    Update[Asset](sql).updateMany(assets)
  }

  def insertAssets2(assets: List[Asset]): Process[ConnectionIO, Asset] = {
    val sql = "INSERT INTO asset (aclass, altname, decimals, display_decimals) VALUES (?, ?, ?, ?)"
    Update[Asset](sql).updateManyWithGeneratedKeys[Asset]("asset_id")(assets)
  }

  def findAssetByClass(aclass: String): Query0[(String, String, Int, Int)] = {
    sql"""SELECT * FROM asset WHERE aclass = $aclass""".query[(String, String, Int, Int)]
  }

}