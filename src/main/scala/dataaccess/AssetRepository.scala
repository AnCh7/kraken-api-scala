package dataaccess

import models.Asset

trait AssetRepository {

  def createTable(): Boolean
  def dropTable(): Boolean
  def delete(id: Int): Int
  def insert(asset: Asset): Long
  def insert(assets: Seq[Asset]): Seq[Long]
  def update(id: Int, asset: Asset): Int
  def getAll: Seq[Asset]
  def findByAltName(value: String): Seq[Asset]
  def findByAClass(value: String): Seq[Asset]
}