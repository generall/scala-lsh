name := "lsh"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies ++= Seq(
  "info.debatty" % "java-lsh" % "0.9"
)

resolvers += Resolver.mavenLocal
