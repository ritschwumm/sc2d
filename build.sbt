Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / versionScheme := Some("early-semver")

name			:= "sc2d"
organization	:= "de.djini"
version			:= "0.40.0"

scalaVersion	:= "2.13.5"
scalacOptions	++= Seq(
	"-feature",
	"-deprecation",
	"-unchecked",
	"-Werror",
	"-Xlint",
)

conflictManager	:= ConflictManager.strict withOrganization "^(?!(org\\.scala-lang|org\\.scala-js)(\\..*)?)$"

libraryDependencies	++= Seq(
	"io.monix"	%%	"minitest"	%	"2.9.3"	%	"test"
)

testFrameworks	+= new TestFramework("minitest.runner.Framework")

wartremoverErrors ++= Seq(
	Wart.StringPlusAny,
	Wart.EitherProjectionPartial,
	Wart.OptionPartial,
	Wart.Enumeration,
	Wart.FinalCaseClass,
	Wart.JavaConversions,
	Wart.Option2Iterable,
	Wart.TryPartial
)
