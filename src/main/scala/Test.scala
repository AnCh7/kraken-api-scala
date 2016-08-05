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


// http://tpolecat.github.io/doobie-0.3.0/01-Introduction.html
//https://github.com/tpolecat/doobie/blob/series/0.3.x/yax/example/src/main/scala/example/FirstExample.scala
//https://github.com/Jacoby6000/scoobie/blob/master/postgres/src/test/scala/scoobie/doobie/PostgresTest.scala#L71
//https://medium.com/@allawala.omar/embracing-functional-scala-3d688719de25#.dzg8vrtag
object DoobieExample {

  import doobie.imports._, scalaz.effect.IO
  import doobie.imports._
  import scalaz._, Scalaz._, scalaz.concurrent.Task

  def main(args: Array[String]): Unit = {

    val xa = DriverManagerTransactor[IO](
      "org.postgresql.Driver", "jdbc:postgresql:world", "postgres", "postgres"
    )

    case class Country(code: String, name: String, population: Long)
    case class City(id: String, name: String, countrycode: String, district: String, population: String)

    def find(n: String): ConnectionIO[Option[Country]] = {
      sql"SELECT code, name, population FROM country WHERE name = $n".query[Country].option
    }

    def findCity(n: String): ConnectionIO[Option[City]] = {
      sql"SELECT id, name, countrycode, district, population FROM city WHERE name = $n".query[City].option
    }

    def insertCity(name: String, countrycode: String, district: String, population: Int): Update0 =
      sql"INSERT INTO city (id, name, countrycode, district, population) VALUES (1101010, $name, $countrycode, $district, $population)".update

    //    val city = findCity("Minsk").transact(xa).unsafePerformIO
    //    println(city)

    insertCity("Minskkk", "BLR", "Test", 100).run

    //    val c = find("Blrs").transact(xa).unsafePerformIO
    //    println(c)

  }
}


