package restapi

import models.AssetInfo.AssetInfo
import models.Interval.Interval
import models._
import responses._
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import common.Helper

import scala.collection.immutable.HashMap
import scala.util.{Failure, Success, Try}

class KrakenApi {

  private val PROTOCOL = "https://"
  private val DOMAIN = "api.kraken.com"
  private val VERSION = "/0/"
  private val PUBLIC_ENDPOINT = "public/"
  private val API_URL = PROTOCOL + DOMAIN + VERSION + PUBLIC_ENDPOINT

  private val mapper = new ObjectMapper()
    .registerModule(DefaultScalaModule)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  private val retrofit = new Retrofit.Builder()
    .baseUrl(API_URL)
    .addConverterFactory(JacksonConverterFactory.create(mapper))
    .build()

  private val api = retrofit.create(classOf[KrakenPublicApi])

  private def createResponse[A](response: Response[A]): Either[Seq[String], A] = {
    if (response.error.nonEmpty)
      Left(response.error)
    else
      Right(response.result)
  }

  private def process[A, B](tr: Try[retrofit2.Response[Response[A]]], mapping: (A, Seq[String]) => Either[Seq[String], B]) = {
    tr match {
      case Success(s) => {
        if (s.isSuccessful) {
          if (s.body().error.isEmpty) mapping(s.body().result, s.body().error)
          else throw new Exception(s.body().error.mkString("/"))
        }
        else throw new Exception(s.errorBody().string())
      }
      case Failure(f) => throw f
    }
  }

  /**
    * Get server time.
    *
    * @param tries retry count.
    * @return Server's time.
    * @note This is to aid in approximating the skew time between the server and client.
    */
  def getServerTime(tries: Int = 0): Either[Seq[String], TimeResponse] = {
    Helper.retry(tries) {
      def mapping: (TimeResponse, Seq[String]) => Either[Seq[String], TimeResponse] = {
        (body: TimeResponse, errors: Seq[String]) => {
          createResponse(Response[TimeResponse](errors, body))
        }
      }
      val response = Try(api.time().execute())
      process[TimeResponse, TimeResponse](response, mapping)
    }
  }

  /**
    * Get asset info.
    *
    * @param info   info to retrieve (optional). Default - info (all info).
    * @param aClass asset class (optional). Default - currency.
    * @param asset  comma delimited list of assets to get info on (optional). Default - all (for given asset class).
    * @param tries  retry count.
    * @return Array of asset names and their info.
    */
  def getAssetInfo(info: String = "info",
                   aClass: String = "currency",
                   asset: String = null,
                   tries: Int = 0): Either[Seq[String], Seq[AssetsResponse]] = {
    Helper.retry(tries) {
      def mapping: (HashMap[String, Asset], Seq[String]) => Either[Seq[String], Seq[AssetsResponse]] = {
        (body: HashMap[String, Asset], errors: Seq[String]) => {
          val arr = body.map(x => AssetsResponse(x._1, x._2)).toSeq
          createResponse(Response[Seq[AssetsResponse]](errors, arr))
        }
      }
      val response = Try(api.assets(info, aClass, asset).execute())
      process[HashMap[String, Asset], Seq[AssetsResponse]](response, mapping)
    }
  }

  /**
    * Get ticker information.
    *
    * @param pair  comma delimited list of asset pairs to get info on. For example: DAOETH, XBTGBP.
    * @param tries retry count.
    * @return Array of pair names and their ticker info.
    */
  def getTickerInfo(pair: Seq[String], tries: Int = 0): Either[Seq[String], Seq[TickerResponse]] = {
    Helper.retry(tries) {
      def mapping: (HashMap[String, Ticker], Seq[String]) => Either[Seq[String], Seq[TickerResponse]] = {
        (body: HashMap[String, Ticker], errors: Seq[String]) => {
          val arr = body.map(x => TickerResponse(x._1, x._2)).toSeq
          createResponse(Response[Seq[TickerResponse]](errors, arr))
        }
      }
      val response = Try(api.ticker(pair.mkString(",")).execute())
      process[HashMap[String, Ticker], Seq[TickerResponse]](response, mapping)
    }
  }

  /**
    * Get OHLC data.
    *
    * @param pair     asset pair to get OHLC data for.
    * @param interval time frame interval in minutes (optional). 1 (default), 5, 15, 30, 60, 240, 1440, 10080, 21600.
    * @param since    return committed OHLC data since given id (optional, exclusive).
    * @param tries    retry count.
    * @return Array of pair name and OHLC data.
    */
  def getOHLCdata(pair: String,
                  since: String = null,
                  interval: Interval = Interval.`1`,
                  tries: Int = 0): Either[Seq[String], OhlcResponse] = {
    Helper.retry(tries) {
      def mapping: (HashMap[String, Object], Seq[String]) => Either[Seq[String], OhlcResponse] = {
        (body: HashMap[String, Object], errors: Seq[String]) => {
          val name = body.head._1
          val last = body.last._2.toString.toInt
          val data = body.head._2.asInstanceOf[Seq[Seq[Object]]].toSeq
          val ohlcResponse = OhlcResponse(name, data.map(x => MarketData(x)), last)
          createResponse(Response[OhlcResponse](errors, ohlcResponse))
        }
      }
      val response = Try(api.ohlc(pair, interval.toString, since).execute())
      process[HashMap[String, Object], OhlcResponse](response, mapping)
    }
  }

  /**
    * Get tradable asset pairs.
    *
    * @param info  info to retrieve (optional). Possible values:
    *              info = all info (default),
    *              leverage = leverage info,
    *              fees = fees schedule,
    *              margin = margin info.
    * @param pair  comma delimited list of asset pairs to get info on (optional). Default = all.
    * @param tries retry count.
    * @return Array of pair names and their info.
    * @note Note: If an asset pair is on a maker/taker fee schedule, the taker side is given in "fees" and maker side in "fees_maker".
    *       For pairs not on maker/taker, they will only be given in "fees".
    */
  def getTradableAssets(pair: Seq[String] = Seq.empty,
                        info: AssetInfo = AssetInfo.All,
                        tries: Int = 0): Either[Seq[String], Seq[AssetPairResponse]] = {
    Helper.retry(tries) {
      def mapping: (HashMap[String, AssetPair], Seq[String]) => Either[Seq[String], Seq[AssetPairResponse]] = {
        (body: HashMap[String, AssetPair], errors: Seq[String]) => {
          val arr = body.map(x => AssetPairResponse(x._1, x._2)).toSeq
          createResponse(Response[Seq[AssetPairResponse]](errors, arr))
        }
      }
      val response = Try(api.assetPairs(info.toString, if (pair.isEmpty) null else pair.mkString(",")).execute())
      process[HashMap[String, AssetPair], Seq[AssetPairResponse]](response, mapping)
    }
  }

  /**
    * Get order book.
    *
    * @param pair  asset pair to get market depth for.
    * @param count maximum number of asks/bids (optional).
    * @param tries retry count.
    * @return Array of pair name and market depth.
    */
  def getOrderBook(pair: String, count: Int, tries: Int = 0): Either[Seq[String], OrderBookResponse] = {
    // val r = Try(api.depth(pair, count).execute())
    ???
  }

  /**
    *
    * @param pair  asset pair to get trade data for.
    * @param since return trade data since given id (optional, exclusive).
    * @param tries retry count.
    * @return Array of pair name and recent trade data.
    */
  def getRecentTrades(pair: String, since: String, tries: Int = 0): Either[Seq[String], Seq[TradesResponse]] = {
    // val r = Try(api.trades(pair, since).execute())
    ???
  }

  /**
    * Get recent spread data.
    *
    * @param pair  asset pair to get spread data for.
    * @param since return spread data since given id (optional, inclusive).
    * @param tries retry count.
    * @return Array of pair name and recent spread data.
    * @note Note: "since" is inclusive so any returned data with the same time
    *       as the previous set should overwrite all of the previous set's entries at that time
    */
  def getRecentSpreadData(pair: String, since: String, tries: Int = 0): Either[Seq[String], SpreadResponse] = {
    // val r = Try(api.spread(pair, since).execute())
    ???
  }
}

