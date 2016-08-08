name := "kraken-api-scala"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.commons" % "commons-dbcp2" % "2.1.1"

libraryDependencies += "org.postgresql" % "postgresql" % "9.4.1209.jre7"
libraryDependencies += "com.h2database" % "h2" % "1.4.192"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"

libraryDependencies += "org.scalikejdbc" %% "scalikejdbc" % "2.4.2"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc-test" % "2.4.2" % "test"
libraryDependencies += "org.scalikejdbc" % "scalikejdbc-config_2.11" % "2.4.2"

libraryDependencies += "com.squareup.retrofit2" % "retrofit" % "2.1.0"
libraryDependencies += "com.squareup.retrofit2" % "converter-gson" % "2.1.0"
libraryDependencies += "com.squareup.retrofit2" % "converter-jackson" % "2.1.0"
libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.5"

libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.2" % "test"
