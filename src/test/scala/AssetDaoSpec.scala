import dataaccess.AssetDao
import models.Asset
import org.scalatest._
import scalikejdbc.config

class AssetDaoSpec extends FlatSpec with Matchers with OptionValues with Inside with Inspectors {

  config.DBs.setupAll()

  AssetDao.dropTable()
  AssetDao.createTable()

  var asset1 = Asset("currency", "ETH", 10, 5)
  var asset2 = Asset("currency", "XBT", 10, 5)
  var updateAsset = Asset("currency", "ETH2", 10, 5)
  var invalidAsset = Asset(null, null, 0, 0)
  var assets = Seq(asset1, asset2)

  "Database" should "be empty in the beginning" in {
    AssetDao.getAll match {
      case Right(r) => assert(r.isEmpty)
      case Left(l) => fail()
    }
  }

  it should "be 1 asset in database with id 1 after inserting an asset" in {
    AssetDao.insert(asset1) match {
      case Right(id) => assert(id === 1)
      case Left(l) => fail()
    }
  }

  it should "be 1 asset in database with id 2 after inserting an asset" in {
    AssetDao.insert(asset2) match {
      case Right(id) => assert(id === 2)
      case Left(l) => fail()
    }
  }

  it should "find 2 assets by 'aClass' field equals 'currency'" in {
    AssetDao.findByAClass("currency") match {
      case Right(r) => assert(r.length === 2)
      case Left(l) => fail()
    }
  }

  it should "be 0 assets in database after deleting asset 1 and 2" in {
    AssetDao.delete(1) match {
      case Right(id) => assert(id === 1)
      case Left(l) => fail()
    }
    AssetDao.delete(2) match {
      case Right(id) => assert(id === 1)
      case Left(l) => fail()
    }
    AssetDao.getAll match {
      case Right(r) => assert(r.isEmpty)
      case Left(l) => fail()
    }
  }

  it should "be 2 assets in database after batch insert" in {
    AssetDao.insert(assets) match {
      case Right(ids) => assert(ids.length === 2)
      case Left(l) => fail()
    }

    AssetDao.getAll match {
      case Right(r) => assert(r.length === 2)
      case Left(l) => fail()
    }
  }

  it should "be changed asset's 'altname' field in database after update call" in {
    AssetDao.update(3, updateAsset) match {
      case Right(id) => assert(id === 1)
      case Left(l) => fail()
    }
    AssetDao.findByAltName(updateAsset.altname) match {
      case Right(r) =>
        assert(r.length === 1)
        assert(r.head.altname === "ETH2")
      case Left(l) => fail()
    }
  }

  it should "throw error if inserting invalid asset" in {
    AssetDao.insert(invalidAsset) match {
      case Right(r) => fail()
      case Left(l) => assert(l === "ERROR: null value in column \"aclass\" violates not-null constraint\n  Detail: Failing row contains (5, null, null, 0, 0).")
    }
  }
}
