import restapi.KrakenApi
import org.scalatest._

class KrakenApiSpec extends FunSuite {

  private val api = new KrakenApi()

  test("getServerTime method returns valid response") {
    val result = api.getServerTime()
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(!response.rfc1123.isEmpty)
        assert(!response.unixtime.isNaN)
      }
    }
  }

  test("getAssetInfo method returns valid response") {
    val result = api.getAssetInfo()
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(response.nonEmpty)
        assert(!response.head.name.isEmpty)
        assert(!response.head.asset.aclass.isEmpty)
        assert(!response.head.asset.altname.isEmpty)
        assert(!response.head.asset.decimals.isNaN)
        assert(!response.head.asset.display_decimals.isNaN)
      }
    }
  }

  test("getAssetInfo method throws error with invalid asset") {
    intercept[Exception] {
      api.getAssetInfo(asset = "BYN")
    }
  }

  test("getAssetInfo method throws error with expected message") {
    try {
      api.getAssetInfo(asset = "BYN")
    } catch {
      case e: Exception => assert(e.getMessage == "EQuery:Unknown asset")
    }
  }

  ignore("getAssetInfo method throws error without connection with expected message") {
    try {
      api.getAssetInfo()
    } catch {
      case e: Exception => assert(e.getMessage == "api.kraken.com")
    }
  }

  test("getTickerInfo method returns valid response") {
    val result = api.getTickerInfo(Seq("DAOETH", "DAOEUR"))
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(response.nonEmpty)
        assert(!response.head.name.isEmpty)
        assert(response.head.result.a.nonEmpty)
        assert(response.head.result.b.nonEmpty)
        assert(response.head.result.c.nonEmpty)
        assert(response.head.result.h.nonEmpty)
        assert(response.head.result.l.nonEmpty)
        assert(!response.head.result.o.isNaN)
        assert(response.head.result.p.nonEmpty)
        assert(response.head.result.t.nonEmpty)
        assert(!response.head.result.v.isEmpty)
      }
    }
  }

  test("getOHLCdata method returns valid response with pair parameter") {
    val result = api.getOHLCdata("DAOETH")
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(!response.name.isEmpty)
        assert(!response.last.isNaN)
        assert(response.data.nonEmpty)
        assert(!response.data.head.close.isNaN)
        assert(!response.data.head.count.isNaN)
        assert(!response.data.head.high.isNaN)
        assert(!response.data.head.low.isNaN)
        assert(!response.data.head.open.isNaN)
        assert(!response.data.head.time.isNaN)
        assert(!response.data.head.volume.isNaN)
        assert(!response.data.head.vwap.isNaN)
      }
    }
  }

  test("getTradableAssets method returns valid response with pair parameter") {
    val result = api.getTradableAssets(Seq("DAOETH"))
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(response.nonEmpty)
        assert(response.head.name.nonEmpty)
        assert(response.head.data.altname.nonEmpty)
        assert(response.head.data.aclass_base.nonEmpty)
        assert(response.head.data.base.nonEmpty)
        assert(response.head.data.aclass_quote.nonEmpty)
        assert(response.head.data.quote.nonEmpty)
        assert(response.head.data.lot.nonEmpty)
        assert(!response.head.data.pair_decimals.isNaN)
        assert(!response.head.data.lot_decimals.isNaN)
        assert(!response.head.data.lot_multiplier.isNaN)
        //assert(response.head.data.leverage_buy.nonEmpty)
        //assert(response.head.data.leverage_sell.nonEmpty)
        assert(response.head.data.fees.nonEmpty)
        assert(response.head.data.fees_maker.nonEmpty)
        assert(response.head.data.fee_volume_currency.nonEmpty)
        assert(!response.head.data.margin_call.isNaN)
        assert(!response.head.data.margin_stop.isNaN)
      }
    }
  }

}


