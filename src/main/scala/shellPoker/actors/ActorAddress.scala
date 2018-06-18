package shellPoker.actors

import akka.actor.{ActorContext, ActorSelection, ActorSystem}

/* Represents some actor address in certain actor system on certain machine. */
case class ActorAddress(systemName: String, relativePath: String, hostIP: String, port: Int) {

  /* Creates valid actor path for obtaining actor selections from remote systems. */
  def completeRemotePath: String = {

    s"${ActorsConfig.akkaRemoteProtocol}://${systemName}@${hostIP}:${port}${relativePath}"
  }


  /* Obtains actor selection from given actor context. */
  def selection(context: ActorContext): ActorSelection = {

    val path = completeRemotePath
    context.actorSelection(path)
  }
}
