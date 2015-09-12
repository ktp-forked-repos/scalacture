import sbt.Keys._
import sbt._

object Build extends Build {
  val project = "scalacture"

  val basicSettings = Seq(
    version := "0.1",
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.10.5", "2.11.7")
  )

  lazy val core = Project(
    id = s"$project-core",
    base = file(s"$project-core"),
    settings = basicSettings ++ Seq(
      name := s"$project-core",
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "2.2.4" % "test",
        "org.scalacheck" %% "scalacheck" % "1.12.4" % "test"
      )
    )
  )

  lazy val scalaz = Project(
    id = s"$project-scalaz",
    base = file(s"$project-scalaz"),
    settings = basicSettings ++ Seq(
      name := s"$project-scalaz",
      testFrameworks += new TestFramework("scalaprops.ScalapropsFramework"),
      parallelExecution in Test := false,
      libraryDependencies ++= Seq(
        "org.scalaz" %% "scalaz-core" % "7.1.3",
        "com.github.scalaprops" %% "scalaprops" % "0.1.13" % "test",
        "com.github.scalaprops" %% "scalaprops-scalazlaws" % "0.1.13" % "test"
      )
    )
  ).dependsOn(
    core
  )

  lazy val experimental = Project(
    id = s"$project-experimental",
    base = file(s"$project-experimental"),
    settings = basicSettings ++ Seq(
      name := s"$project-experimental"
    )
  ).dependsOn(
    core,
    core % "test->test"
  )
}
