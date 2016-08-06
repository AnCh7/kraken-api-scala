package dataaccess

import scalaz._, Scalaz._
import scalaz.effect.{SafeApp, IO}
import scalaz.stream.Process
import doobie.imports._

case class Coffee(name: String, supId: Int, price: Double, sales: Int, total: Int)

case class Country(code: String, name: String, population: Long)

case class City(id: Int, name: String, countrycode: String, district: String, population: Int)

class KrakenDao {

  val coffees = List(
    Coffee("Colombian", 101, 7.99, 0, 0),
    Coffee("French_Roast", 49, 8.99, 0, 0),
    Coffee("Espresso", 150, 9.99, 0, 0),
    Coffee("Colombian_Decaf", 101, 8.99, 0, 0),
    Coffee("French_Roast_Decaf", 49, 9.99, 0, 0)
  )

  def insertCity(cs: List[City]): ConnectionIO[Int] =
    Queries.insertCity.updateMany(cs)

  //  def coffeesLessThan(price: Double): Process[ConnectionIO, (String, String)] =
  //    Queries.coffeesLessThan(price).process

//  def insertCoffees(cs: List[Coffee]): ConnectionIO[Int] =
//    Queries.insertCoffee.updateMany(cs)
//
//  def allCoffees: Process[ConnectionIO, Coffee] =
//    Queries.allCoffees.process

  //  def create: ConnectionIO[Unit] =
  //    Queries.create.run.void

}

object Queries {

  //  def coffeesLessThan(price: Double): Query0[(String, String)] =
  //    sql"""
  //        SELECT cof_name, sup_name
  //        FROM coffees JOIN suppliers ON coffees.sup_id = suppliers.sup_id
  //        WHERE price < $price
  //      """.query[(String, String)]

  val insertCity: Update[City] =
    Update[City]("INSERT INTO city VALUES (?, ?, ?, ?, ?)", None)

//  def insertCity(name: String, countrycode: String, district: String, population: Int): Update0 =
//    sql"INSERT INTO city (id, name, countrycode, district, population) VALUES (1101010, $NAME, $countrycode, $district, $population)".update

  //  def allCoffees[A]: Query0[Coffee] =
  //    sql"SELECT cof_name, sup_id, price, sales, total FROM coffees".query[Coffee]

  //  def create: Update0 =
  //    sql"""
  //        CREATE TABLE suppliers (
  //          sup_id   INT     NOT NULL PRIMARY KEY,
  //          sup_name VARCHAR NOT NULL,
  //          street   VARCHAR NOT NULL,
  //          city     VARCHAR NOT NULL,
  //          state    VARCHAR NOT NULL,
  //          zip      VARCHAR NOT NULL
  //        );
  //        CREATE TABLE coffees (
  //          cof_name VARCHAR NOT NULL,
  //          sup_id   INT     NOT NULL,
  //          price    DOUBLE  NOT NULL,
  //          sales    INT     NOT NULL,
  //          total    INT     NOT NULL
  //        );
  //        ALTER TABLE coffees
  //        ADD CONSTRAINT coffees_suppliers_fk FOREIGN KEY (sup_id) REFERENCES suppliers(sup_id);
  //      """.update

}
