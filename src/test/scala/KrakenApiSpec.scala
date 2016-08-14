import restapi.KrakenApi
import org.scalatest._

class KrakenApiSpec extends FunSuite {

  private val api = new KrakenApi()

  test("getServerTime method returns valid response") {
    val result = api.getServerTime()
    result match {
      case Left(errors) => fail()
      case Right(response) =>
        assert(!response.rfc1123.isEmpty)
        assert(!response.unixtime.isNaN)
    }
  }

  test("getAssetInfo method returns valid response") {
    val result = api.getAssetInfo()
    result match {
      case Left(errors) => fail()
      case Right(response) =>
        assert(response.nonEmpty)
        assert(!response.head.name.isEmpty)
        assert(!response.head.result.aclass.isEmpty)
        assert(!response.head.result.altname.isEmpty)
        assert(!response.head.result.decimals.isNaN)
        assert(!response.head.result.display_decimals.isNaN)
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
      case Right(response) =>
        assert(response.nonEmpty)
        assert(!response.head.name.isEmpty)
        assert(response.head.result.a.nonEmpty)
        assert(response.head.result.b.nonEmpty)
        assert(response.head.result.c.nonEmpty)
        assert(response.head.result.h.nonEmpty)
        assert(response.head.result.l.nonEmpty)
        assert(response.head.result.o.nonEmpty)
        assert(response.head.result.p.nonEmpty)
        assert(response.head.result.t.nonEmpty)
        assert(response.head.result.v.nonEmpty)
    }
  }

  test("getOHLCdata method returns valid response with pair parameter") {
    val result = api.getOHLCdata("DAOETH")
    result match {
      case Left(errors) => fail()
      case Right(response) =>
        assert(!response.name.isEmpty)
        assert(!response.last.isNaN)
        assert(response.result.nonEmpty)
        assert(!response.result.head.close.isNaN)
        assert(!response.result.head.count.isNaN)
        assert(!response.result.head.high.isNaN)
        assert(!response.result.head.low.isNaN)
        assert(!response.result.head.open.isNaN)
        assert(!response.result.head.time.isNaN)
        assert(!response.result.head.volume.isNaN)
        assert(!response.result.head.vwap.isNaN)
    }
  }

  test("getTradableAssets method returns valid response with pair parameter") {
    val result = api.getTradableAssets(Seq("DAOETH"))
    result match {
      case Left(errors) => fail()
      case Right(response) =>
        assert(response.nonEmpty)
        assert(response.head.name.nonEmpty)
        assert(response.head.result.altname.nonEmpty)
        assert(response.head.result.aclass_base.nonEmpty)
        assert(response.head.result.base.nonEmpty)
        assert(response.head.result.aclass_quote.nonEmpty)
        assert(response.head.result.quote.nonEmpty)
        assert(response.head.result.lot.nonEmpty)
        assert(!response.head.result.pair_decimals.isNaN)
        assert(!response.head.result.lot_decimals.isNaN)
        assert(!response.head.result.lot_multiplier.isNaN)
        //assert(response.head.result.leverage_buy.nonEmpty)
        //assert(response.head.result.leverage_sell.nonEmpty)
        assert(response.head.result.fees.nonEmpty)
        assert(response.head.result.fees_maker.nonEmpty)
        assert(response.head.result.fee_volume_currency.nonEmpty)
        assert(!response.head.result.margin_call.isNaN)
        assert(!response.head.result.margin_stop.isNaN)
    }
  }

  test("getOrderBook method returns valid response valid parameters") {
    val result = api.getOrderBook("DAOETH")
    result match {
      case Left(errors) => fail()
      case Right(response) =>
        assert(response.name.nonEmpty)
        assert(response.result.asks.nonEmpty)
        assert(response.result.bids.nonEmpty)
        assert(!response.result.bids.head.price.isNaN)
        assert(!response.result.bids.head.timestamp.isNaN)
        assert(!response.result.bids.head.volume.isNaN)
    }
  }

  test("getRecentTrades method returns valid response valid parameters") {
    val result = api.getRecentTrades("DAOETH")
    result match {
      case Left(errors) => fail()
      case Right(response) =>
        assert(response.last.nonEmpty)
        assert(response.name.nonEmpty)
        assert(response.trades.nonEmpty)
        assert(response.trades.head.TradeType.toString.nonEmpty)
        assert(response.trades.head.OrderType.toString.nonEmpty)
        //assert(response.trades.head.miscellaneous.nonEmpty)
        assert(!response.trades.head.price.isNaN)
        assert(!response.trades.head.time.isNaN)
        assert(!response.trades.head.volume.isNaN)
    }
  }

  test("getRecentSpreadData method returns valid response valid parameters") {
    val result = api.getRecentSpreadData("DAOETH")
    result match {
      case Left(errors) => fail()
      case Right(response) =>
        assert(!response.last.isNaN)
        assert(response.name.nonEmpty)
        assert(response.data.nonEmpty)
        assert(!response.data.head.ask.isNaN)
        assert(!response.data.head.bid.isNaN)
        assert(!response.data.head.time.isNaN)
    }
  }
}


