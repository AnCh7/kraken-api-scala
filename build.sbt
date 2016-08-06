name := "kraken-api-scala"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.json4s" % "json4s-native_2.11" % "3.4.0"

libraryDependencies += "org.tpolecat" % "doobie-core_2.11" % "0.3.0"
libraryDependencies += "org.tpolecat" % "doobie-contrib-specs2_2.11" % "0.3.0"
libraryDependencies += "org.tpolecat" % "doobie-contrib-postgresql_2.11" % "0.3.0"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.4"

libraryDependencies += "com.squareup.retrofit2" % "retrofit" % "2.1.0"
libraryDependencies += "com.squareup.retrofit2" % "converter-gson" % "2.1.0"
libraryDependencies += "com.squareup.retrofit2" % "converter-jackson" % "2.1.0"
libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.5"

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"
