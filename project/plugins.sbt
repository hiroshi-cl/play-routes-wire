val playVersion = System.getProperties.getProperty("play.version", "2.8.1")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % playVersion)
