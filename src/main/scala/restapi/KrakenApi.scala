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

import scala.collection.immutable.AbstractMap

object KrakenApi {

  private val PROTOCOL = "https://"
  private val DOMAIN = "api.kraken.com"
  private val VERSION = "/0/"
  private val PUBLIC_ENDPOINT = "public/"
  private val API_URL = PROTOCOL + DOMAIN + VERSION + PUBLIC_ENDPOINT

  // Use the jackson converter with the scala module registered,
  // so that jackson knows how to handle the standard scala types.
  private val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  private val retrofit = new Retrofit.Builder()
    .baseUrl(API_URL)
    .addConverterFactory(JacksonConverterFactory.create(mapper))
    .build()

  private val api = retrofit.create(classOf[KrakenPublicApi])

  private def createResponse[A](response: Response[A]): Either[List[String], A] = {
    if (response.error.nonEmpty)
      Left(response.error)
    else
      Right(response.result)
  }

  /**
    * Get server time.
    *
    * @return Server's time.
    * @note This is to aid in approximating the skew time between the server and client.
    */
  def getServerTime: Either[List[String], TimeResponse] = {
    val response = api.time().execute().body()
    createResponse(response)
  }

  /**
    * Get asset info.
    *
    * @param info   info to retrieve (optional). Default - info (all info).
    * @param aClass asset class (optional). Default - currency.
    * @param asset  comma delimited list of assets to get info on (optional). Default - all (for given asset class).
    * @return Array of asset names and their info.
    */
  def getAssetInfo(info: String = "info",
                   aClass: String = "currency",
                   asset: String = "all"): Either[List[String], Array[AssetsResponse]] = {
    val response = api.assets(info, aClass, asset).execute().body()
    createResponse(response)
    null
  }

  /**
    * Get ticker information.
    *
    * @param pair comma delimited list of asset pairs to get info on. For example: DAOETH, XBTGBP.
    * @return Array of pair names and their ticker info.
    */
  def getTickerInfo(pair: Array[String]): Either[List[String], Array[TickerResponse]] = {
    val response = api.ticker(pair.mkString(",")).execute().body()
    createResponse(response)
    null
  }

  /**
    * Get OHLC data.
    *
    * @param pair     asset pair to get OHLC data for.
    * @param interval time frame interval in minutes (optional). 1 (default), 5, 15, 30, 60, 240, 1440, 10080, 21600.
    * @param since    return committed OHLC data since given id (optional, exclusive).
    * @return Array of pair name and OHLC data.
    */
  def getOHLCdata(pair: String,
                  since: String = null,
                  interval: Interval = Interval.`1`): Either[List[String], OhlcResponse] = {

    val body = api.ohlc(pair, interval.toString, since).execute().body()
    val name = body.result.head._1
    val last = body.result.last._2.toString.toInt
    val data = body.result.head._2.asInstanceOf[List[List[Object]]]

    val ohlcResponse = OhlcResponse(name, data.map(x => MarketData(x.head.toString.toInt, x(1).toString.toDouble,
                                                   x(2).toString.toDouble, x(3).toString.toDouble,
                                                   x(4).toString.toDouble, x(5).toString.toDouble,
                                                   x(6).toString.toDouble, x(7).toString.toInt)).toArray, last)
    createResponse(Response[OhlcResponse](body.error, ohlcResponse))
  }

  /**
    * Get tradable asset pairs.
    *
    * @param info info to retrieve (optional). Possible values:
    *             info = all info (default),
    *             leverage = leverage info,
    *             fees = fees schedule,
    *             margin = margin info.
    * @param pair comma delimited list of asset pairs to get info on (optional). Default = all.
    * @return Array of pair names and their info.
    * @note Note: If an asset pair is on a maker/taker fee schedule, the taker side is given in "fees" and maker side in "fees_maker".
    *       For pairs not on maker/taker, they will only be given in "fees".
    */
  def getTradableAssets(pair: Array[String],
                        info: AssetInfo = AssetInfo.All): Either[List[String], Array[AssetPairResponse]] = {
    val response = api.assetPairs(info.toString, pair.mkString(",")).execute().body()
    createResponse(response)
    null
  }

  /**
    * Get order book.
    *
    * @param pair  asset pair to get market depth for.
    * @param count maximum number of asks/bids (optional).
    * @return Array of pair name and market depth.
    */
  def getOrderBook(pair: String, count: Int): Either[List[String], OrderBookResponse] = {
    val response = api.depth(pair, count).execute().body()
    createResponse(response)
    null
  }

  /**
    *
    * @param pair  asset pair to get trade data for.
    * @param since return trade data since given id (optional, exclusive).
    * @return Array of pair name and recent trade data.
    */
  def getRecentTrades(pair: String, since: String): Either[List[String], Array[TradesResponse]] = {
    val response = api.trades(pair, since).execute().body()
    createResponse(response)
    null
  }

  /**
    * Get recent spread data.
    *
    * @param pair  asset pair to get spread data for.
    * @param since return spread data since given id (optional, inclusive).
    * @return Array of pair name and recent spread data.
    * @note Note: "since" is inclusive so any returned data with the same time
    *       as the previous set should overwrite all of the previous set's entries at that time
    */
  def getRecentSpreadData(pair: String, since: String): Either[List[String], SpreadResponse] = {
    val response = api.spread(pair, since).execute().body()
    createResponse(response)
    null
  }
}

