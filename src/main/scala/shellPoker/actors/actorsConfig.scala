import com.typesafe.config.{Config, ConfigFactory}

/* Parses configurations for remote actor system with custom IP address and port. */
def getRemoteCustomConfig(ip: String, port: Int): Config = {

  val configStr: String =
    s"""akka {
      |  actor {
      |    provider = remote
      |    allow-java-serialization=off
      |    debug {
      |      receive = on
      |    }
      |  }
      |  remote {
      |    log-sent-messages=on
      |    enabled-transports = ["akka.remote.netty.tcp"]
      |    netty.tcp {
      |      hostname = "${ip}"
      |      port = ${port}
      |    }
      | }
      |}""".stripMargin


  val regularConfig: Config = ConfigFactory.load
  val customConfig: Config = ConfigFactory.parseString(configStr)

  // override regular stack with myConfig
  val combined = customConfig.withFallback(regularConfig)
  combined
}


/* Creates valid actor path for obtaining actor selections from remote systems. */
def getRemoteActorPath(systemName: String, relativeActorPath: String, ip: String, port: Int): String = {

  s"akka.tcp://${systemName}@${ip}:${port}${relativeActorPath}"
}