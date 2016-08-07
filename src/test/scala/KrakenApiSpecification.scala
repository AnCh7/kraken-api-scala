import restapi.KrakenApi
import org.scalatest._

class KrakenApiSpecification extends FunSuite {

  test("getServerTime method returns valid response") {
    val result = KrakenApi.getServerTime
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(!response.rfc1123.isEmpty)
        assert(!response.unixtime.isNaN)
      }
    }
  }

  test("getAssetInfo method returns valid response") {
    val result = KrakenApi.getAssetInfo()
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(!response.isEmpty)
        assert(!response.head.name.isEmpty)
        assert(!response.head.asset.aclass.isEmpty)
        assert(!response.head.asset.altname.isEmpty)
        assert(!response.head.asset.decimals.isNaN)
        assert(!response.head.asset.display_decimals.isNaN)
      }
    }
  }

  test("getTickerInfo method returns valid response") {
    val result = KrakenApi.getTickerInfo(Array("DAOETH", "DAOEUR"))
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(!response.isEmpty)
        assert(!response.head.name.isEmpty)
        assert(!response.head.result.a.isEmpty)
        assert(!response.head.result.b.isEmpty)
        assert(!response.head.result.c.isEmpty)
        assert(!response.head.result.h.isEmpty)
        assert(!response.head.result.l.isEmpty)
        assert(!response.head.result.o.isNaN)
        assert(!response.head.result.p.isEmpty)
        assert(!response.head.result.t.isEmpty)
        assert(!response.head.result.v.isEmpty)
      }
    }
  }

  test("getOHLCdata method returns valid response with pair parameter") {
    val result = KrakenApi.getOHLCdata("DAOETH")
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(!response.name.isEmpty)
        assert(!response.last.isNaN)
        assert(!response.data.isEmpty)
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

}


