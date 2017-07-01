package fr.xebia.csp.eratosthenes.primes

import akka.actor.{Actor, ActorRef, Props}
import fr.xebia.csp.eratosthenes.primes.Messages.{Filter, Find, Result}

import scala.collection.mutable

class Master(upper: Int, actors: ActorRefs) extends Actor {
  var source: ActorRef = _

  def receive: Receive = {
    case Find =>
      source = sender
      val masterRef = context.actorSelection(s"/user/${Worker.name}")
      (2 to upper) foreach { n => masterRef ! Filter(n) }

    case Result(list) =>
      source ! list
  }

}

object Master {

  def props(upper: Int, actors: mutable.HashMap[String, ActorRef]) =
    Props(new Master(upper, actors))

  val name: String = "master"
}
