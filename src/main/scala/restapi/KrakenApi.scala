package restapi

import retrofit2.{Response, Retrofit}

import org.json4s._
import org.json4s.jackson.JsonMethods._

import okhttp3.ResponseBody

import scala.util.{Failure, Success, Try}

import common.Helper

import models.AssetInfo._
import models.Interval._
import models._
import responses._

class KrakenApi {

  private val PROTOCOL = "https://"
  private val DOMAIN = "api.kraken.com"
  private val VERSION = "/0/"
  private val PUBLIC_ENDPOINT = "public/"
  private val API_URL = PROTOCOL + DOMAIN + VERSION + PUBLIC_ENDPOINT

  implicit val formats = DefaultFormats
  private val retrofit = new Retrofit.Builder().baseUrl(API_URL).build()
  private val api = retrofit.create(classOf[KrakenPublicApi])

  private def process[A, B](tr: Try[Response[ResponseBody]], mapping: (JValue, Seq[String]) => Either[Seq[String], B]) = {
    tr match {
      case Success(s) =>
        if (s.isSuccessful) {
          if (s.errorBody() == null) {
            val response = parse(s.body().string()).extract[KrakenResponse[JValue]]
            if (response.error.isEmpty) mapping(response.result, response.error)
            else throw new Exception(response.error.mkString("/"))
          }
          else throw new Exception(s.errorBody().string())
        }
        else throw new Exception(s.errorBody().string())
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
      val response = Try(api.time().execute())
      process[JValue, TimeResponse](response, Mapping.serverTime)
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
      val response = Try(api.assets(info, aClass, asset).execute())
      process[JValue, Seq[AssetsResponse]](response, Mapping.assetInfo)
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
      val response = Try(api.ticker(pair.mkString(",")).execute())
      process[JValue, Seq[TickerResponse]](response, Mapping.tickerInfo)
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
      val response = Try(api.ohlc(pair, interval.toString, since).execute())
      process[JValue, OhlcResponse](response, Mapping.ohlcData)
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
      val response = Try(api.assetPairs(info.toString, if (pair.isEmpty) null else pair.mkString(",")).execute())
      process[JValue, Seq[AssetPairResponse]](response, Mapping.tradableAssets)
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
  def getOrderBook(pair: String, count: Int = 0, tries: Int = 0): Either[Seq[String], OrderBookResponse] = {
    Helper.retry(tries) {
      val response = Try(api.depth(pair, count).execute())
      process[JValue, OrderBookResponse](response, Mapping.orderBook)
    }
  }

  /**
    *
    * @param pair  asset pair to get trade data for.
    * @param since return trade data since given id (optional, exclusive).
    * @param tries retry count.
    * @return Array of pair name and recent trade data.
    */
  def getRecentTrades(pair: String, since: String = null, tries: Int = 0): Either[Seq[String], TradesResponse] = {
    Helper.retry(tries) {
      val response = Try(api.trades(pair, since).execute())
      process[JValue, TradesResponse](response, Mapping.recentTrades)
    }
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
  def getRecentSpreadData(pair: String, since: String = null, tries: Int = 0): Either[Seq[String], SpreadResponse] = {
    Helper.retry(tries) {
      val response = Try(api.spread(pair, since).execute())
      process[JValue, SpreadResponse](response, Mapping.recentSpreadData)
    }
  }
}

