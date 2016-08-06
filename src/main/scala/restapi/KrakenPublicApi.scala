package restapi

import responses._
import retrofit2.Call

trait KrakenPublicApi {

  type get = retrofit2.http.GET
  type post = retrofit2.http.POST
  type path = retrofit2.http.Path
  type query = retrofit2.http.Query

  @get("Time")
  def time(): Call[Response[TimeResponse]]

  @get("Assets")
  def assets(@query("info") info: String,
             @query("aclass") aClass: String,
             @query("asset") asset: String): Call[Response[AssetsResponse]]

  @get("Ticker")
  def ticker(@query("pair") pair: String): Call[Response[TickerResponse]]

  @get("OHLC")
  def ohlc(@query("pair") pair: String,
           @query("interval") interval: String,
           @query("since") since: String): Call[Response[OhlcResponse]]

  @get("AssetPairs")
  def assetPairs(@query("info") info: String, @query("pair") pair: String): Call[Response[AssetPairResponse]]
}

