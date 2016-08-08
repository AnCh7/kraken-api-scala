package dataaccess

import models.Asset
import scalikejdbc._

object AssetDao extends AssetRepository {

  config.DBs.setupAll()

  override def createTable(): Boolean = DB localTx { implicit session =>
    sql"""CREATE TABLE asset (
      asset_id           SERIAL      NOT NULL PRIMARY KEY,
      aclass             VARCHAR     NOT NULL,
      altname            VARCHAR     NOT NULL,
      decimals           INTEGER     NOT NULL,
      display_decimals   INTEGER     NOT NULL);""".execute.apply()
  }

  override def dropTable(): Boolean = DB localTx { implicit session =>
    sql"DROP TABLE IF EXISTS asset".execute.apply()
  }

  override def insert(asset: Asset): Long = DB localTx { implicit session =>
    sql"""INSERT INTO asset (aclass, altname, decimals, display_decimals) VALUES (
          ${asset.aclass},
          ${asset.altname},
          ${asset.decimals},
          ${asset.display_decimals})""".stripMargin.updateAndReturnGeneratedKey.apply()
  }

  override def insert(assets: Seq[Asset]): Seq[Long] = DB localTx { implicit session =>
    sql"INSERT INTO asset (aclass, altname, decimals, display_decimals) VALUES (?, ?, ?, ?)"
      .batchAndReturnGeneratedKey(assets.map(a => Seq(a.aclass, a.altname, a.decimals, a.display_decimals)): _*)
      .apply()
  }

  override def delete(id: Int): Int = DB localTx { implicit session =>
    sql"DELETE FROM asset WHERE asset_id = ${id}".update.apply()
  }

  override def update(id: Int, asset: Asset): Int = DB localTx { implicit session =>
    sql"""UPDATE asset
          SET aclass = ${asset.aclass},
              altname = ${asset.altname},
              decimals = ${asset.decimals},
              display_decimals = ${asset.display_decimals}
          WHERE asset_id = ${id}""".update.apply()
  }

  override def getAll: Seq[Asset] = DB readOnly { implicit session =>
    sql"SELECT * FROM asset"
      .map(rs => Asset(rs.string("aclass"), rs.string("altname"), rs.int("decimals"), rs.int("display_decimals")))
      .list().apply()
  }

  override def findByAltName(value: String): Seq[Asset] = DB readOnly { implicit session =>
    sql"SELECT * FROM asset WHERE altname = ${value}"
      .map(rs => Asset(rs.string("aclass"), rs.string("altname"), rs.int("decimals"), rs.int("display_decimals")))
      .list.apply()
  }

  override def findByAClass(value: String): Seq[Asset] = DB readOnly { implicit session =>
    sql"SELECT * FROM asset WHERE aclass = ${value}"
      .map(rs => Asset(rs.string("aclass"), rs.string("altname"), rs.int("decimals"), rs.int("display_decimals")))
      .list.apply()
  }

}