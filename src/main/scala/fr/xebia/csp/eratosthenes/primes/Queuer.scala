package fr.xebia.csp.eratosthenes.primes

import akka.actor.{Actor, ActorRef, Props}
import fr.xebia.csp.eratosthenes.primes.Messages.{Enqueue, Merge, Result}

import scala.collection.mutable

class Queuer(upper: Int, actors: ActorRefs) extends Actor {
  private var other: Option[List[Int]] = None
  private var expectedMessages = ((upper - 1) * 2) - 1

  private val workerRef = context.actorSelection(s"/user/${Worker.name}")
  private val masterRef = context.actorSelection(s"/user/${Master.name}")

  def receive: Receive = {
    case Enqueue(list) =>
      expectedMessages -= 1
      if (expectedMessages == 0) {
        masterRef ! Result(list)
      }
      else {
        process(list)
      }
  }

  private def process(list: List[Int]) {
    other match {
      case Some(o) =>
        workerRef ! Merge(list, o)
        other = None
      case None =>
        other = Some(list)
    }
  }
}

object Queuer {
  val name: String = "queuer"

  def props(upper: Int, actors: mutable.HashMap[String, ActorRef]) =
    Props(new Queuer(upper, actors))
}
