package fr.xebia.csp.eratosthenes.primes

import akka.actor.{Actor, ActorRef, Props}
import fr.xebia.csp.eratosthenes.primes.Master.Messages.{Find, Result}

class Master(upper: Int) extends Actor {
  var source: ActorRef = _

  def receive: Receive = {
    case Find =>
      source = sender
      val masterRef = context.actorSelection(s"/user/${Worker.name}")
      (2 to upper) foreach { n => masterRef ! Worker.Messages.Filter(n) }

    case Result(list) =>
      source ! list
  }

}

object Master {

  def props(upper: Int) =
    Props(new Master(upper))

  val name: String = "master"

  object Messages {

    final case object Find

    final case class Result(list: List[Int])

  }

}
