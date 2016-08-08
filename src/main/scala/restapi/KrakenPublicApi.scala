package restapi

import models._
import responses._
import retrofit2.Call

import scala.collection.immutable.HashMap

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
             @query("asset") asset: String): Call[Response[HashMap[String, Asset]]]

  @get("Ticker")
  def ticker(@query("pair") pair: String): Call[Response[HashMap[String, Ticker]]]

  @get("OHLC")
  def ohlc(@query("pair") pair: String,
           @query("interval") interval: String,
           @query("since") since: String): Call[Response[HashMap[String, Object]]]

  @get("AssetPairs")
  def assetPairs(@query("info") info: String, @query("pair") pair: String): Call[Response[HashMap[String, AssetPair]]]

  @get("Depth")
  def depth(@query("pair") pair: String, @query("count") count: Int): Call[Response[OrderBookResponse]]

  @get("Trades")
  def trades(@query("pair") pair: String, @query("since") since: String): Call[Response[TradesResponse]]

  @get("Spread")
  def spread(@query("pair") pair: String, @query("since") since: String): Call[Response[SpreadResponse]]
}

