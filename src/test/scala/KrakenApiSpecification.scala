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

  test("getOHLCdata method returns valid response with pair parameter") {
    val result = KrakenApi.getOHLCdata("DAOETH")
    result match {
      case Left(errors) => fail()
      case Right(response) => {
        assert(!response.name.isEmpty)
        assert(!response.last.isNaN)
        assert(!response.data.isEmpty)
      }
    }
  }

}


