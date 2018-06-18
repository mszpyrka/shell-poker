package shellPoker.actors


import com.typesafe.config.{Config, ConfigFactory}


object ActorsConfig {
  /* Parses configurations for remote actor system with custom IP address and port. */
  def getSystemConfig(ip: String, port: Int): Config = {

    val configStr: String =
      s"""akka {
         |  actor {
         |
         |    akka.actor.allow-java-serialization = off
         |    akka.actor.warn-about-java-serializer-usage = off
         |
         |
         |    provider = remote
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

    // override regular stack with customConfig
    val combined = customConfig.withFallback(regularConfig)
    combined
  }


  /* Protocol used for sending messages to remote actor systems. */
  val akkaRemoteProtocol: String = "akka.tcp"
}

