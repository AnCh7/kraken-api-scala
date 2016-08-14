package restapi

import models._
import org.json4s._
import responses._

object Mapping {

  implicit val formats = DefaultFormats

  private def createResponse[A](response: KrakenResponse[A]): Either[Seq[String], A] = {
    if (response.error.nonEmpty)
      Left(response.error)
    else
      Right(response.result)
  }

  def serverTime: (JValue, Seq[String]) => Either[Seq[String], TimeResponse] = {
    (result: JValue, errors: Seq[String]) => {
      createResponse(KrakenResponse[TimeResponse](errors, result.extract[TimeResponse]))
    }
  }

  def assetInfo: (JValue, Seq[String]) => Either[Seq[String], Seq[AssetsResponse]] = {
    (result: JValue, errors: Seq[String]) => {
      val response = result.extract[Map[String, JValue]].map(x => AssetsResponse(x._1, x._2.extract[Asset])).toSeq
      createResponse(KrakenResponse[Seq[AssetsResponse]](errors, response))
    }
  }

  def tickerInfo: (JValue, Seq[String]) => Either[Seq[String], Seq[TickerResponse]] = {
    (result: JValue, errors: Seq[String]) => {
      val response = result.extract[Map[String, JValue]].map(x => TickerResponse(x._1, x._2.extract[Ticker])).toSeq
      createResponse(KrakenResponse[Seq[TickerResponse]](errors, response))
    }
  }

  def ohlcData: (JValue, Seq[String]) => Either[Seq[String], OhlcResponse] = {
    (result: JValue, errors: Seq[String]) => {
      val obj = result.extract[Map[String, JValue]]
      val name = obj.head._1
      val last = obj.last._2.extract[Int]
      val data = obj.head._2.extract[Seq[Seq[String]]]
      val response = OhlcResponse(name, data.map(x => MarketData(x)), last)
      createResponse(KrakenResponse[OhlcResponse](errors, response))
    }
  }

  def tradableAssets: (JValue, Seq[String]) => Either[Seq[String], Seq[AssetPairResponse]] = {
    (result: JValue, errors: Seq[String]) => {
      val response = result.extract[Map[String, JValue]].map(x => AssetPairResponse(x._1, x._2.extract[AssetPair])).toSeq
      createResponse(KrakenResponse[Seq[AssetPairResponse]](errors, response))
    }
  }

  def orderBook: (JValue, Seq[String]) => Either[Seq[String], OrderBookResponse] = {
    (result: JValue, errors: Seq[String]) => {
      val obj = result.extract[Map[String, JValue]]
      val name = obj.head._1
      val orders = obj.head._2.extract[Map[String, Seq[Seq[String]]]].values
      val asks = orders.head.map(x => PriceLevel(x(0).toDouble, x(1).toDouble, x(2).toInt))
      val bids = orders.last.map(x => PriceLevel(x(0).toDouble, x(1).toDouble, x(2).toInt))
      val response = OrderBookResponse(name, OrderBook(asks, bids))
      createResponse(KrakenResponse[OrderBookResponse](errors, response))
    }
  }

  def recentTrades: (JValue, Seq[String]) => Either[Seq[String], TradesResponse] = {
    (result: JValue, errors: Seq[String]) => {
      val obj = result.extract[Map[String, JValue]]
      val name = obj.head._1
      val trades = obj.head._2.extract[Seq[Seq[String]]]
      val last = obj.last._2.extract[String]
      val response = TradesResponse(name, trades.map(x => Trade(x)), last)
      createResponse(KrakenResponse[TradesResponse](errors, response))
    }
  }

  def recentSpreadData: (JValue, Seq[String]) => Either[Seq[String], SpreadResponse] = {
    (result: JValue, errors: Seq[String]) => {
      val obj = result.extract[Map[String, JValue]]
      val name = obj.head._1
      val last = obj.last._2.extract[Int]
      val data = obj.head._2.extract[Seq[Seq[String]]]
      val response = SpreadResponse(name, data.map(x => Spread(x)), last)
      createResponse(KrakenResponse[SpreadResponse](errors, response))
    }
  }
}
