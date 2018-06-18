package shellPoker.actors

import akka.actor.{ActorRef, ActorSelection, ActorSystem}
import scala.concurrent.Await
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

/* Provides some useful methods for easy communication with actors. */
object ActorCommunicationHelper {

  /* Sends message to given actor and waits for response for specified time. */
  def askWithTimeout[MessageType, ResultType](
      actor: ActorRef,
      message: MessageType,
      time: Int
    ): ResultType = {

    implicit val timeout: Timeout = Timeout(time seconds)
    val future = actor ? message
    val result = Await.result(future, timeout.duration).asInstanceOf[ResultType]
    result
  }
}
