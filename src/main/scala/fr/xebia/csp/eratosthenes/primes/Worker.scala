package fr.xebia.csp.eratosthenes.primes

import akka.actor.{Actor, Props}
import akka.routing.RoundRobinPool
import fr.xebia.csp.eratosthenes.primes.Worker.Messages.{Filter, Merge}

class Worker(upper: Int) extends Actor {
  private val queuerRef = context.actorSelection(s"/user/${Queuer.name}")

  def receive: Receive = {
    case Filter(n) =>
      queuerRef ! Queuer.Messages.Enqueue((2 to upper).toList filter (x => x == n || x % n != 0))

    case Merge(left, right) =>
      queuerRef ! Queuer.Messages.Enqueue(left intersect right)
  }
}

object Worker {
  val name: String = "worker"

  def props(upper: Int, nrOfWorkers: Int) =
    Props(new Worker(upper)) withRouter RoundRobinPool(nrOfWorkers)

  object Messages {

    final case class Filter(divisor: Int)

    final case class Merge(left: List[Int], right: List[Int])

  }

}
