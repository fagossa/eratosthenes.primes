package fr.xebia.csp.eratosthenes.primes

import akka.actor.{Actor, Props}
import fr.xebia.csp.eratosthenes.primes.Master.Messages.Result
import fr.xebia.csp.eratosthenes.primes.Queuer.Messages.Enqueue

class Queuer(upper: Int) extends Actor {
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
        workerRef ! Worker.Messages.Merge(list, o)
        other = None
      case None =>
        other = Some(list)
    }
  }
}

object Queuer {
  val name: String = "queuer"

  def props(upper: Int) =
    Props(new Queuer(upper))

  object Messages {

    final case class Enqueue(list: List[Int])

  }

}
