//object RetrofitExample {

//  def main(args: Array[String]): Unit = {
//
//    val ticker = Bitfinex.getTicker("BTCUSD")
//    val x = 0
//  }
//}
//
//object Json4sExample {
//
//  def main(args: Array[String]): Unit = {
//
//    import org.json4s._
//    import org.json4s.native.JsonMethods._
//    implicit val formats = DefaultFormats
//
//    case class Model(hello: String, age: Int)
//    val rawJson = """{"hello": "world", "age": 42}"""
//
//    println(parse(rawJson).extract[Model])
//  }
//}

object DoobieExample {

  import doobie.imports._, scalaz.effect.IO
  import doobie.imports._
  import scalaz._, Scalaz._, scalaz.concurrent.Task

  def main(args: Array[String]): Unit = {

    val xa = DriverManagerTransactor[IO](
      "org.postgresql.Driver", "jdbc:postgresql:world", "postgres", "postgres"
    )

    //    def find(n: String): ConnectionIO[Option[Country]] = {
    //      sql"SELECT code, name, population FROM country WHERE name = $n".query[Country].option
    //    }
    //
    //    def findCity(n: String): ConnectionIO[Option[City]] = {
    //      sql"SELECT id, name, countrycode, district, population FROM city WHERE name = $n".query[City].option
    //    }


    //    val city = findCity("Minsk").transact(xa).unsafePerformIO
    //    println(city)

    //insertCity("Minskkk", "BLR", "Test", 100).run

    //    val c = find("Blrs").transact(xa).unsafePerformIO
    //    println(c)

  }
}


