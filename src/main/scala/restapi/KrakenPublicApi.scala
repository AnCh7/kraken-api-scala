package restapi

import okhttp3.ResponseBody
import retrofit2.Call

trait KrakenPublicApi {

  type get = retrofit2.http.GET
  type post = retrofit2.http.POST
  type path = retrofit2.http.Path
  type query = retrofit2.http.Query

  @get("Time")
  def time(): Call[ResponseBody]

  @get("Assets")
  def assets(@query("info") info: String, @query("aclass") aClass: String, @query("asset") asset: String): Call[ResponseBody]

  @get("Ticker")
  def ticker(@query("pair") pair: String): Call[ResponseBody]

  @get("OHLC")
  def ohlc(@query("pair") pair: String, @query("interval") interval: String, @query("since") since: String): Call[ResponseBody]

  @get("AssetPairs")
  def assetPairs(@query("info") info: String, @query("pair") pair: String): Call[ResponseBody]

  @get("Depth")
  def depth(@query("pair") pair: String, @query("count") count: Int): Call[ResponseBody]

  @get("Trades")
  def trades(@query("pair") pair: String, @query("since") since: String): Call[ResponseBody]

  @get("Spread")
  def spread(@query("pair") pair: String, @query("since") since: String): Call[ResponseBody]
}

