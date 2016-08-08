package dataaccess

import models.Asset
import scalikejdbc._

object AssetDao extends AssetRepository {

  config.DBs.setupAll()

  override def createTable(): Either[String, Boolean] = DB localTx { implicit session =>
    try {
      Right(
        sql"""CREATE TABLE asset (
      asset_id           SERIAL      NOT NULL PRIMARY KEY,
      aclass             VARCHAR     NOT NULL,
      altname            VARCHAR     NOT NULL,
      decimals           INTEGER     NOT NULL,
      display_decimals   INTEGER     NOT NULL);""".execute.apply())
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  override def dropTable(): Either[String, Boolean] = DB localTx { implicit session =>
    try {
      Right(sql"DROP TABLE IF EXISTS asset".execute.apply())
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  override def insert(asset: Asset): Either[String, Long] = DB localTx { implicit session =>
    try {
      Right(
        sql"""INSERT INTO asset (aclass, altname, decimals, display_decimals) VALUES (
          ${asset.aclass},
          ${asset.altname},
          ${asset.decimals},
          ${asset.display_decimals})""".stripMargin.updateAndReturnGeneratedKey.apply())
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  override def insert(assets: Seq[Asset]): Either[String, Seq[Long]] = DB localTx { implicit session =>
    try {
      Right(
        sql"INSERT INTO asset (aclass, altname, decimals, display_decimals) VALUES (?, ?, ?, ?)"
          .batchAndReturnGeneratedKey(assets.map(a => Seq(a.aclass, a.altname, a.decimals, a.display_decimals)): _*)
          .apply())
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  override def delete(id: Int): Either[String, Int] = DB localTx { implicit session =>
    try {
      Right(sql"DELETE FROM asset WHERE asset_id = ${id}".update.apply())
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  override def update(id: Int, asset: Asset): Either[String, Int] = DB localTx { implicit session =>
    try {
      Right(
        sql"""UPDATE asset
          SET aclass = ${asset.aclass},
              altname = ${asset.altname},
              decimals = ${asset.decimals},
              display_decimals = ${asset.display_decimals}
          WHERE asset_id = ${id}""".update.apply())
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  override def getAll: Either[String, Seq[Asset]] = DB readOnly { implicit session =>
    try {
      Right(
        sql"SELECT * FROM asset"
          .map(mapAsset)
          .list().apply())
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  override def findByAltName(value: String): Either[String, Seq[Asset]] = DB readOnly { implicit session =>
    try {
      Right(
        sql"SELECT * FROM asset WHERE altname = ${value}"
          .map(mapAsset)
          .list.apply())
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  override def findByAClass(value: String): Either[String, Seq[Asset]] = DB readOnly { implicit session =>
    try {
      Right(
        sql"SELECT * FROM asset WHERE aclass = ${value}"
          .map(mapAsset)
          .list.apply())
    } catch {
      case e: Exception => Left(e.getMessage)
    }
  }

  private def mapAsset: (WrappedResultSet) => Asset = {
    (rs: WrappedResultSet) => {
      Asset(rs.string("aclass"), rs.string("altname"), rs.int("decimals"), rs.int("display_decimals"))
    }
  }
}