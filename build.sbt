Global / onChangedBuildSource := ReloadOnSourceChanges

name			:= "sc2d"
organization	:= "de.djini"
version			:= "0.38.0"

scalaVersion	:= "2.13.4"
scalacOptions	++= Seq(
	"-feature",
	"-deprecation",
	"-unchecked",
	"-Werror",
	"-Xlint",
)

conflictManager	:= ConflictManager.strict withOrganization "^(?!(org\\.scala-lang|org\\.scala-js)(\\..*)?)$"

libraryDependencies	++= Seq(
	"io.monix"	%%	"minitest"	%	"2.9.2"	%	"test"
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
