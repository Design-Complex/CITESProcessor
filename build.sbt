//name := "CITESProcessor"
//
//version := "0.1"
//
//scalaVersion := "2.13.2"
//
//import Dependencies._
//import sbt.io.{IO, Path, FileFilter}

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "io.designcomplex",
      scalaVersion := "2.12.10"
    )),
    name := "fp-scala-homework",
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-unchecked",
      "-language:postfixOps",
      "-language:higherKinds", // HKT required for Monads and other HKT types
      "-Ypartial-unification" // PU required for better type inference
    ),
    libraryDependencies ++= Dependencies.core ++ Dependencies.scalaTest,
    mainClass in assembly := Some("io.designcomplex.MainApp"),
    assemblyJarName in assembly := "CITESProcessor.jar",
    test in assembly := {},
    // ignore lib refs in jars
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    }
  )

lazy val zipHomework = taskKey[Unit]("zip files for homework submission")

zipHomework := {
  val bd = baseDirectory.value
  val targetFile = s"${bd.getAbsolutePath}/e88-final.zip"
  val ignoredPaths = ".*(\\.idea|target|\\.DS_Store)/*".r.pattern
  val fileFilter = new FileFilter {
    override def accept(f: File) = !ignoredPaths.matcher(f.getAbsolutePath).lookingAt
  }
  println("zipping project files ...")
  IO.delete(new File(targetFile))
  IO.zip(
    Path.selectSubpaths(
      new File(bd.getAbsolutePath), fileFilter),
    new File(targetFile))
}
