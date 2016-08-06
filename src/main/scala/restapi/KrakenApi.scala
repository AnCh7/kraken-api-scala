package restapi

import models.AssetInfo.AssetInfo
import models.Interval.Interval
import models._
import responses._
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KrakenApi {

  val PROTOCOL = "https://"
  val DOMAIN = "api.kraken.com"
  val VERSION = "/0/"
  val PUBLIC_ENDPOINT = "public/"
  val PRIVATE_ENDPOINT = "private/"
  val API_URL = PROTOCOL + DOMAIN + VERSION + PUBLIC_ENDPOINT

  /**
    * Get server time.
    *
    * @return Server's time.
    * @note This is to aid in approximating the skew time between the server and client.
    */
  def getServerTime(): TimeResponse = {

    val retrofit = new Retrofit.Builder()
      .baseUrl(API_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    val api = retrofit.create(classOf[KrakenPublicApi])
    val response = api.time().execute()
    null
  }

  /**
    * Get asset info.
    *
    * @param info   info to retrieve (optional). Default - info (all info).
    * @param aClass asset class (optional). Default - currency.
    * @param asset  comma delimited list of assets to get info on (optional). Default - all (for given asset class).
    * @return Array of asset names and their info.
    */
  def getAssetInfo(info: String = "info", aClass: String = "currency", asset: String = "all"): Array[AssetsResponse] = {

    val retrofit = new Retrofit.Builder()
      .baseUrl(API_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    val api = retrofit.create(classOf[KrakenPublicApi])
    val response = api.assets(info, aClass, asset).execute()
    response.body()
    null
  }

  /**
    * Get ticker information.
    *
    * @param pair comma delimited list of asset pairs to get info on. For example: DAOETH, XBTGBP.
    * @return array of pair names and their ticker info.
    */
  def getTickerInfo(pair: Array[String]): Array[TickerResponse] = {

    val retrofit = new Retrofit.Builder()
      .baseUrl(API_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    val api = retrofit.create(classOf[KrakenPublicApi])
    val response = api.ticker(pair.mkString(",")).execute()
    response.body()
    null
  }

  /**
    * Get OHLC data.
    *
    * @param pair     asset pair to get OHLC data for.
    * @param interval time frame interval in minutes (optional). 1 (default), 5, 15, 30, 60, 240, 1440, 10080, 21600.
    * @param since    return committed OHLC data since given id (optional, exclusive).
    * @return array of pair name and OHLC data.
    */
  def getOHLCdata(pair: Array[String], interval: Interval = Interval.`1`, since: String): Array[OhlcResponse] = {

    val retrofit = new Retrofit.Builder()
      .baseUrl(API_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    val api = retrofit.create(classOf[KrakenPublicApi])
    val response = api.ohlc(pair.mkString(","), interval.toString, since).execute()
    response.body()
    null
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
    * @return array of pair names and their info.
    * @note Note: If an asset pair is on a maker/taker fee schedule, the taker side is given in "fees" and maker side in "fees_maker".
    *       For pairs not on maker/taker, they will only be given in "fees".
    */
  def getTradableAssets(info: AssetInfo = AssetInfo.All, pair: Array[String]): Array[AssetPairResponse] = {

    val retrofit = new Retrofit.Builder()
      .baseUrl(API_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    val api = retrofit.create(classOf[KrakenPublicApi])
    val response = api.assetPairs(info.toString, pair.mkString(",")).execute()
    response.body()
    null
  }


  /*
  Get order book
URL: https://api.kraken.com/0/public/Depth

Input:

pair = asset pair to get market depth for
count = maximum number of asks/bids (optional)
Result: array of pair name and market depth

<pair_name> = pair name
    asks = ask side array of array entries(<price>, <volume>, <timestamp>)
    bids = bid side array of array entries(<price>, <volume>, <timestamp>)
   */

}

