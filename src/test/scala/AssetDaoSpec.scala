import dataaccess.AssetDao
import models.Asset
import org.scalatest._
import scalikejdbc._

class AssetDaoSpecs extends FlatSpec with Matchers with OptionValues with Inside with Inspectors {

  config.DBs.setupAll()

  AssetDao.dropTable()
  AssetDao.createTable()

  var asset1 = Asset("currency", "ETH", 10, 5)
  var asset2 = Asset("currency", "XBT", 10, 5)
  var updateAsset = Asset("currency", "ETH2", 10, 5)
  var assets = Array(asset1, asset2)

  "Database" should "be empty in the beginning" in {
    assert(AssetDao.getAll.isEmpty)
  }

  it should "be 1 asset in database with id 1 after inserting an asset" in {
    val id = AssetDao.insert(asset1)
    assert(id === 1)
  }

  it should "be 1 asset in database with id 2 after inserting an asset" in {
    val id = AssetDao.insert(asset2)
    assert(id === 2)
  }

  it should "find 2 assets by 'aClass' field equals 'currency'" in {
    val assets = AssetDao.findByAClass("currency")
    assert(assets.length === 2)
  }

  it should "be 0 assets in database after deleting asset 1 and 2" in {
    val d1 = AssetDao.delete(1)
    val d2 = AssetDao.delete(2)
    assert(d1 === 1)
    assert(d2 === 1)
    assert(AssetDao.getAll.isEmpty)
  }

  it should "be 2 assets in database after batch insert" in {
    val ids = AssetDao.insert(assets)
    assert(ids.head === 3)
    assert(ids.last === 4)
    assert(AssetDao.getAll.length === 2)
  }

  it should "be changed asset's 'altname' field in database after update call" in {
    val id = AssetDao.update(3, updateAsset)
    val asset = AssetDao.findByAltName(updateAsset.altname)
    assert(id === 1)
    assert(asset.length === 1)
    assert(asset.head.altname === "ETH2")
  }

}





