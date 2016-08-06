package restapi

import java.util

import models.MarketData
import responses._
import retrofit2.Call

import scala.collection.immutable.HashMap
import scala.collection.immutable.HashMap.HashMap1

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
           @query("since") since: String): Call[Response[HashMap[String, Object]]]

  @get("AssetPairs")
  def assetPairs(@query("info") info: String, @query("pair") pair: String): Call[Response[AssetPairResponse]]

  @get("Depth")
  def depth(@query("pair") pair: String, @query("count") count: Int): Call[Response[OrderBookResponse]]

  @get("Trades")
  def trades(@query("pair") pair: String, @query("since") since: String): Call[Response[TradesResponse]]

  @get("Spread")
  def spread(@query("pair") pair: String, @query("since") since: String): Call[Response[SpreadResponse]]
}

