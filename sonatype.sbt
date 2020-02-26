import xerial.sbt.Sonatype.GitHubHosting

ThisBuild / credentials ++= (for {
  username <- sys.env.get("SONATYPE_USERNAME")
  password <- sys.env.get("SONATYPE_PASSWORD")
} yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)).toSeq

ThisBuild / publishTo := sonatypePublishTo.value

ThisBuild / sonatypeProfileName := "com.github.hiroshi-cl"
ThisBuild / sonatypeProjectHosting := Some(GitHubHosting("hiroshi-cl", "play-routes-wire", ""))
