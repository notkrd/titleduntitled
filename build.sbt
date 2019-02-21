import com.typesafe.sbt.packager.MappingsHelper._

name := "homoFauxNighIsThis"
 
version := "1.0" 
      
lazy val `homofauxnighisthis` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

mappings in Universal ++= directory(baseDirectory.value / "resources")