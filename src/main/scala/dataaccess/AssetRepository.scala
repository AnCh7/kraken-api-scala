package dataaccess

import models.Asset

trait AssetRepository {
  def createTable(): Either[String, Boolean]
  def dropTable(): Either[String, Boolean]
  def delete(id: Int): Either[String, Int]
  def insert(asset: Asset): Either[String, Long]
  def insert(assets: Seq[Asset]): Either[String, Seq[Long]]
  def update(id: Int, asset: Asset): Either[String, Int]
  def getAll: Either[String, Seq[Asset]]
  def findByAltName(value: String): Either[String, Seq[Asset]]
  def findByAClass(value: String): Either[String, Seq[Asset]]
}