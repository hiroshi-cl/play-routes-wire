val playVersion = sys.props.get("play.version").getOrElse("2.8.1")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % playVersion)
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.1")
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.8.1")
