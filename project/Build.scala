import sbt._
import Keys._

/**
 * MacroBuild is a sub project that compiles our macros
 * before the actual project is built
 *
 * Due to either a limitation (or not knowing how yet), you need to publish
 * both the main root project and the macro project, i.e. you need to do
 * publish-local and macro/publish-local
 *
 * For more info, have a look here
 * http://stackoverflow.com/questions/18400538/macro-dependancy-appearing-in-pom-jar
 */

object MacroBuild extends Build {
  lazy val main = Project("main", file(".")) dependsOn(macroSub) settings(
    // include the macro classes and resources in the main jar
    mappings in (Compile, packageBin) ++= mappings.in(macroSub, Compile, packageBin).value,
    // include the macro sources in the main source jar
    mappings in (Compile, packageSrc) ++= mappings.in(macroSub, Compile, packageSrc).value
    )
  lazy val macroSub = Project("macro", file("macro")) settings(
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
//    publish := {},
//    publishLocal := {}
    )
}