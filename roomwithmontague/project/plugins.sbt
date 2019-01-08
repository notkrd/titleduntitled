logLevel := Level.Debug

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.2.1")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.13")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.3")