
def twitterUtil(mod: String) =
  "com.twitter" %% s"util-$mod" %  "6.45.0"

def finagle(mod: String) =
  "com.twitter" %% s"finagle-$mod" % "6.45.0"

def linkerd(mod: String) =
  "io.buoyant" %% s"linkerd-$mod" % "1.3.2"

val headerClassifier =
  project.in(file("header-classifier")).
    settings(
      scalaVersion := "2.12.4",
      organization := "io.buoyant",
      name := "header-classifier",
      resolvers ++= Seq(
        "twitter" at "https://maven.twttr.com",
        "local-m2" at ("file:" + Path.userHome.absolutePath + "/.m2/repository")
      ),
      libraryDependencies ++=
        "org.apache.httpcomponents" % "httpclient" % "4.5.4" ::
        finagle("http") % "provided" ::
        twitterUtil("core") % "provided" ::
        linkerd("core") % "provided" ::
        linkerd("protocol-http") % "provided" ::
        Nil,
      assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
    )
